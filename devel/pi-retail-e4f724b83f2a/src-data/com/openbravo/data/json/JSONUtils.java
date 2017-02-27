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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class JSONUtils {

    private JSONUtils() {
    }

    public static JSONArray checkResult(JSONObject json) throws BasicException {
        try {
            if (json.has("_entityName")) {
                return new JSONArray().put(json);
            } else {
                JSONObject response = json.getJSONObject("response");
                int status = response.getInt("status");
                if (status == 0) {
                    return response.getJSONArray("data");
                } else {
                    if (response.has("errors")) {
                        throw new BasicException(response.getJSONObject("errors").getString("id"));
                    } else {
                        throw new BasicException(response.getJSONObject("error").getString("message"));
                    }
                }
            }
        } catch (JSONException ex) {
            throw new BasicException("Not valid JSON response", ex);
        }
    }

    public static ResultProcess checkProcessResult(JSONObject json) throws BasicException {
        try {
            JSONObject response = json.getJSONObject("response");
            int status = response.getInt("status");
            if (status == 0) {
                ResultProcess result = new ResultProcess();
                result.setResult(response.getInt("result"));
                result.setRecord(response.getString("record"));
                result.setMessage(response.getString("message"));
                return result;
            } else {
                if (response.has("errors")) {
                    throw new BasicException(response.getJSONObject("errors").getString("id"));
                } else {
                    throw new BasicException(response.getJSONObject("error").getString("message"));
                }
            }
        } catch (JSONException ex) {
            throw new BasicException("Not valid JSON response", ex);
        }
    }
}
