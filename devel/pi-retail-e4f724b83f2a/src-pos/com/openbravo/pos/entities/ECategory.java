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
import java.awt.image.BufferedImage;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ECategory extends EBean {

    private String name = null;
    private String parent = null;
    private BufferedImage image = null;

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("parentPOSCategory", JSONFormats.STRING.writeJSON(getParent()));
        json.put("bindaryData", JSONFormats.BYTEA.writeJSON(ImageUtils.writeImage(getImage())));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setParent(JSONFormats.STRING.readJSON(json.get("parentPOSCategory")));
        setImage(ImageUtils.readImage(JSONFormats.BYTEA.readJSON(json.get("bindaryData"))));
    }

    public static ClassEBean<ECategory> getClassEBean() {
        return new ClassEBean<ECategory>() {
            @Override
            public ECategory create() {
                return new ECategory();
            }

            @Override
            public String getEntity() {
                return "OBPOS_CategoriesImage";
            }
        };
    }

    @Override
    public String toString() {
        return name;
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
     * @return the parent
     */
    public String getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * @param imageContent the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
