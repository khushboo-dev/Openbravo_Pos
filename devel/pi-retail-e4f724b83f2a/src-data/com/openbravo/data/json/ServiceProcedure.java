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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ServiceProcedure {

    private static final Logger logger = Logger.getLogger(ServiceProcedure.class.getName());

    private HttpSession httpsession;
    private String process;

    public ServiceProcedure(HttpSession httpsession, String process) {
        this.httpsession = httpsession;
        this.process = process;
    }

    public ResultProcess exec(String record) throws BasicException {
        if (record == null) {
            logger.log(Level.SEVERE, "Cannot process null record identifier: {0}", process);
            throw new BasicException("Cannot process null record identifier: " + process);
        }
        return navigate(record);
    }

    public ResultProcess exec(EBean bean) throws BasicException {
        if (bean == null) {
            logger.log(Level.SEVERE, "Cannot process null object: {0}", process);
            throw new BasicException("Cannot process null object: " + process);
        }
        return navigate(bean.getId());
    }

    private ResultProcess navigate(String record) throws BasicException {
        try {
            HttpNavigate http = new HttpNavigate(httpsession, HttpNavigate.JSONTERMINAL);

            JSONObject data = new JSONObject();
            data.put("process", process);
            data.put("record", record);

            JSONObject response = http.navigate(HttpNavigate.POST, "", data.toString());

            return JSONUtils.checkProcessResult(response);
        } catch (JSONException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }
}
