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
import com.openbravo.pos.util.ThumbNailBuilder;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class EFloor extends EBean {

    private static ThumbNailBuilder tnbcat;

    private String name = null;
    private BufferedImage image = null;

    private Container m_container;
    private Icon m_icon;

    static {
        try {
            tnbcat = new ThumbNailBuilder(32, 32, ImageIO.read(EFloor.class.getClassLoader().getResourceAsStream("com/openbravo/images/atlantikdesigner.png")));
        } catch (IOException ex) {
            Logger.getLogger(EFloor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("image", JSONFormats.BYTEA.writeJSON(ImageUtils.writeImage(getImage())));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setImage(ImageUtils.readImage(JSONFormats.BYTEA.readJSON(json.get("image"))));
    }

    public static ClassEBean<EFloor> getClassEBean() {
        return new ClassEBean<EFloor>() {
            @Override
            public EFloor create() {
                return new EFloor();
            }

            @Override
            public String getEntity() {
                return "OBPOS_Floors";
            }
        };
    }

    public Icon getIcon() {
        return m_icon;
    }
    public Container getContainer() {
        return m_container;
    }

    private static class JPanelDrawing extends JPanel {
        private Image img;

        public JPanelDrawing(Image img) {
            this.img = img;
            setLayout(null);
        }

        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                g.drawImage(img, 0, 0, this);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return (img == null)
                ? new Dimension(640, 480)
                : new Dimension(img.getWidth(this), img.getHeight(this));
        }
        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }
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
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;

        m_container = new JPanelDrawing(image);
        m_icon = new ImageIcon(tnbcat.getThumbNail(image));
    }
}
