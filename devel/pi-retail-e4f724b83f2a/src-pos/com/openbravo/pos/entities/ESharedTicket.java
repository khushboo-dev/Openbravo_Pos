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
import com.openbravo.data.loader.ImageUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ESharedTicket extends EBean {

    private String name = null;
    private String place = null;
    private byte[] content = null;

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("place", JSONFormats.STRING.writeJSON(getPlace()));
        json.put("content", JSONFormats.BYTEA.writeJSON(getContent()));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setPlace(JSONFormats.STRING.readJSON(json.get("place")));
        setContent(JSONFormats.BYTEA.readJSON(json.get("content")));
    }

    public static ClassEBean<ESharedTicket> getClassEBean() {
        return new ClassEBean<ESharedTicket>() {
            @Override
            public ESharedTicket create() {
                return new ESharedTicket();
            }

            @Override
            public String getEntity() {
                return "OBPOS_SharedTickets";
            }
        };
    }

    public EOrder getTicketInfo() {
        EOrder order = (EOrder) ImageUtils.readSerializable(content);
        if (order != null) {
            order.refreshLines();
        }
        return order;
    }

    public void setTicketInfo(EOrder value) {
        setName(value.getName());
        setContent(ImageUtils.writeSerializable(value));
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
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
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
