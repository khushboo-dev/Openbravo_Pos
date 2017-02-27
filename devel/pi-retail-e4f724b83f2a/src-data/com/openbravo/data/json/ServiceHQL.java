//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2011 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.data.json;

import com.openbravo.basic.BasicException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ServiceHQL<T> {

    private static final Logger logger = Logger.getLogger(ServiceHQL.class.getName());

    private HttpSession httpsession;
    private JSONRead<T> read;
    private String hql;

    public ServiceHQL(HttpSession httpsession, JSONRead<T> read, String hql) {
        this.httpsession = httpsession;
        this.read = read;
        this.hql = hql;
    }

    public ServiceHQL(HttpSession httpsession, ClassEBean classEBean, String hql) {
        this.httpsession = httpsession;
        this.read = new JSONReadEBean(classEBean);
        if (hql == null || hql.equals("")) {
            this.hql = "from " + classEBean.getEntity() + " where $readableCriteria";
        } else if (hql.startsWith("order by")) {
            this.hql = "from " + classEBean.getEntity() + " where $readableCriteria " + hql;
        } else if (hql.startsWith("where")) {
            this.hql = "from " + classEBean.getEntity() + " where $readableCriteria and" + hql.substring(5);
        } else {
            this.hql = hql;
        }
    }

    public ServiceHQL(HttpSession httpsession, ClassEBean classEBean) {
        this(httpsession, classEBean, null);
    }

    public T find() throws BasicException {
        return find((HQLParam[]) null);
    }

    public T find(String param) throws BasicException {
        return find(new HQLParam[] {new HQLParam(param)});
    }

    public T find(String[] param) throws BasicException {
        return find(HQLParam.createArray(param));
    }

    public T find(HQLParam param) throws BasicException {
        return find(new HQLParam[] {param});
    }

    public T find(HQLParam[] params) throws BasicException {
        try {
            JSONArray data = navigate(params);
            if (data.length() == 0) {
                return null;
            } else {
                return read.readValues(data.getJSONObject(0));
            }
        } catch (JSONException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }

    public List<T> list() throws BasicException {
        return list((HQLParam[]) null);
    }

    public List<T> list(String param) throws BasicException {
        return list(new HQLParam[] {new HQLParam(param)});
    }

    public List<T> list(String[] param) throws BasicException {
        return list(HQLParam.createArray(param));
    }

    public List<T> list(HQLParam param) throws BasicException {
        return list(new HQLParam[] {param});
    }

    public List<T> list(HQLParam[] params) throws BasicException {
        try {
            JSONArray data = navigate(params);
            List<T> l= new ArrayList<T>(data.length());
            for(int i = 0; i < data.length(); i++) {
                l.add(i, read.readValues(data.getJSONObject(i)));
            }
            return l;
        } catch (JSONException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }

    private JSONArray navigate(HQLParam[] params) throws BasicException {
        try {
            HttpNavigate http = new HttpNavigate(httpsession, HttpNavigate.JSONTERMINAL);

            JSONObject data = new JSONObject();
            data.put("query", hql);

            if (params != null && params.length > 0) {
                JSONObject jsonparams = new JSONObject();
                for (int i = 0; i < params.length; i++) {
                    JSONObject jsonp = new JSONObject();
                    jsonp.put("value", params[i].getType().writeJSON(params[i].getValue()));
                    jsonp.put("type", params[i].getType().getName());
                    if (params[i].getName() == null) {
                        jsonparams.put("parameter" + Integer.toString(i), jsonp);
                    } else {
                        jsonparams.put(params[i].getName(), jsonp);
                    }
                }
                data.put("parameters", jsonparams);
            }

            return JSONUtils.checkResult(http.navigate(HttpNavigate.POST, "", data.toString()));
        } catch (JSONException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }
}
