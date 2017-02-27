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
public class ESharedTicketView extends EBean {

    private String name = null;
    private String place = null;

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("place", JSONFormats.STRING.writeJSON(getPlace()));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setPlace(JSONFormats.STRING.readJSON(json.get("place")));
    }

    public static ClassEBean<ESharedTicketView> getClassEBean() {
        return new ClassEBean<ESharedTicketView>() {
            @Override
            public ESharedTicketView create() {
                return new ESharedTicketView();
            }

            @Override
            public String getEntity() {
                return "OBPOS_SharedTickets";
            }
        };
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the table
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param table the table to set
     */
    public void setPlace(String place) {
        this.place = place;
    }
}
