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
public class EResource extends EBean {

    private String name = null;
    private int type = 0;
    private byte[] content = null;

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("restype", getType());
        json.put("content", JSONFormats.BYTEA.writeJSON(getContent()));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setType(json.getInt("restype"));
        setContent(JSONFormats.BYTEA.readJSON(json.get("content")));
    }

    public static ClassEBean<EResource> getClassEBean() {
        return new ClassEBean<EResource>() {
            @Override
            public EResource create() {
                return new EResource();
            }

            @Override
            public String getEntity() {
                return "OBPOS_Resources";
            }
        };
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
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
}
