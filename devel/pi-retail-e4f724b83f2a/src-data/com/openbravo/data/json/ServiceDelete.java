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
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ServiceDelete<T> {

    private static final Logger logger = Logger.getLogger(ServiceFind.class.getName());

    private HttpSession httpsession;
    private String entity;
    private JSONRead<T> read;

    public ServiceDelete(HttpSession httpsession, ClassEBean clazz) {
        this.httpsession = httpsession;
        entity = clazz.getEntity();
    }

    public ServiceDelete(HttpSession httpsession, String entity) {
        this.httpsession = httpsession;
        this.entity = entity;
    }

    public void exec(String id) throws BasicException {

        try {
            HttpNavigate http = new HttpNavigate(httpsession, HttpNavigate.JSONDEFAULT);
            JSONObject response = http.navigate(HttpNavigate.DELETE, "/" + entity + "/" + id);

            JSONUtils.checkResult(response);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new BasicException(ex);
        }
    }
}
