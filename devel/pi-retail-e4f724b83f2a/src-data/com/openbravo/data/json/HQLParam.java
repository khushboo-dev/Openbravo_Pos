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

/**
 *
 * @author adrian
 */
public class HQLParam {

    private String name;
    private Object value;
    private JSONFormats type;

    public HQLParam(String name, Object value, JSONFormats type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public HQLParam(Object value, JSONFormats type) {
        this(null, value, type);
    }

    public HQLParam(String name, String value) {
        this(name, value, JSONFormats.STRING);
    }

    public HQLParam(String value) {
        this(null, value, JSONFormats.STRING);
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    public static HQLParam[] createArray(String[] values) {
        if (values == null) {
            return null;
        } else {
            HQLParam[] params = new HQLParam[values.length];
            for (int i = 0; i < values.length; i++) {
                params[i] = new HQLParam(values[i]);
            }
            return params;
        }
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return the type
     */
    public JSONFormats getType() {
        return type;
    }

    


}
