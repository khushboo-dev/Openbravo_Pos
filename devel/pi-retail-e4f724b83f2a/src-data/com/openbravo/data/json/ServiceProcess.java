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
public class ServiceProcess<T> {

    private static final Logger logger = Logger.getLogger(ServiceProcess.class.getName());

    private HttpSession httpsession;
    private String classname;
    private JSONRead<T> read;

    public ServiceProcess(HttpSession httpsession, String classname, JSONRead<T> read) {
        this.httpsession = httpsession;
        this.classname = classname;
        this.read = read;
    }

//    public ServiceProcess(HttpSession httpsession, String classname) {
//        this(httpsession, classname, null);
//    }

    public List<T> exec(JSONParam param) throws BasicException {
        try {
            JSONArray data = navigate(param);
            List<T> l= new ArrayList<T>(data.length());

            if (read == null) {
                return null;
            } else {
                for(int i = 0; i < data.length(); i++) {
                    l.add(i, read.readValues(data.getJSONObject(i)));
                }
            }
            return l;
        } catch (JSONException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }

    public List<T> exec() throws BasicException {
        return exec(null);
    }

    private JSONArray navigate(JSONParam param) throws BasicException {
        try {
            HttpNavigate http = new HttpNavigate(httpsession, HttpNavigate.JSONTERMINAL);

            JSONObject data = new JSONObject();
            data.put("className", classname);
            if (param != null) {
                param.writeJSON(data);
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
