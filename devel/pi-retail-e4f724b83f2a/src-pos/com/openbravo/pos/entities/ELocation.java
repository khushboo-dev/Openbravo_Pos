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

package com.openbravo.pos.entities;

import com.openbravo.data.json.ClassEBean;
import com.openbravo.data.json.EBean;
import com.openbravo.data.json.JSONFormats;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ELocation extends EBean {

    private String country;

    public static ClassEBean<ELocation> getClassEBean() {
        return new ClassEBean<ELocation>() {
            @Override
            public ELocation create() {
                return new ELocation();
            }

            @Override
            public String getEntity() {
                return "Location";
            }
        };
    }
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("country", JSONFormats.STRING.writeJSON(getCountry()));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setCountry(JSONFormats.STRING.readJSON(json.opt("country")));
    }
    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
