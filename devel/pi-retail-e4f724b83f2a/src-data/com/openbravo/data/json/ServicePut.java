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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ServicePut<T> {
    private static final Logger logger = Logger.getLogger(ServicePut.class.getName());

    private HttpSession httpsession;
    private JSONRefresh<T> refresh;
    private JSONWrite<T> write;

    public ServicePut(HttpSession httpsession, ClassEBean clazz) {
        this.httpsession = httpsession;
        refresh = new JSONRefreshEBean(clazz);
        write = new JSONWriteEBean(clazz);
    }

    public ServicePut(HttpSession httpsession, JSONRefresh<T> refresh, JSONWrite<T> write) {
        this.httpsession = httpsession;
        this.refresh = refresh;
        this.write = write;
    }

    public T exec(T obj) throws BasicException {

        try {
            HttpNavigate http = new HttpNavigate(httpsession, HttpNavigate.JSONDEFAULT);

            JSONObject json = write.writeValues(obj);
            JSONObject post = new JSONObject();
            post.put("data", json);

            JSONObject response = http.navigate(HttpNavigate.POST, "", null, post.toString());

            JSONArray data = JSONUtils.checkResult(response);
            if (data.length() == 0) {
                return null;
            } else {
                return refresh.refreshValues(obj, data.getJSONObject(0));
            }
        } catch (JSONException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }
}
