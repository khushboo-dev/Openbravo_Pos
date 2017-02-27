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
import java.util.Date;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class EReservation extends EBean {

    private Date datenew;
    private String title;
    private int chairs;
    private boolean done;
    private String description;
    private String businessPartner;
    private String businessPartnerIdentifier;

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("datenew", JSONFormats.TIMESTAMP.writeJSON(getDatenew()));
        json.put("position", JSONFormats.STRING.writeJSON(getTitle()));
        json.put("chairs", getChairs());
        json.put("isdone", isDone());
        json.put("description", JSONFormats.STRING.writeJSON(getDescription()));
        json.put("businessPartner", getBusinessPartner());
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setDatenew(JSONFormats.TIMESTAMP.readJSON(json.opt("datenew")));
        setTitle(JSONFormats.STRING.readJSON(json.opt("position")));
        setChairs(json.optInt("chairs", 0));
        setDone(json.optBoolean("isdone",false));
        setDescription(JSONFormats.STRING.readJSON(json.opt("description")));
        setBusinessPartner(JSONFormats.STRING.readJSON(json.opt("businessPartner")));
        setBusinessPartnerIdentifier(JSONFormats.STRING.readJSON(json.opt("businessPartner._identifier")));
    }

    public static ClassEBean<EReservation> getClassEBean() {
        return new ClassEBean<EReservation>() {
            @Override
            public EReservation create() {
                return new EReservation();
            }

            @Override
            public String getEntity() {
                return "OBPOS_Reservations";
            }
        };
    }

    /**
     * @return the datenew
     */
    public Date getDatenew() {
        return datenew;
    }

    /**
     * @param datenew the datenew to set
     */
    public void setDatenew(Date datenew) {
        this.datenew = datenew;
    }

    /**
     * @return the chairs
     */
    public int getChairs() {
        return chairs;
    }

    /**
     * @param chairs the chairs to set
     */
    public void setChairs(int chairs) {
        this.chairs = chairs;
    }

    /**
     * @return the done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the businessPartner
     */
    public String getBusinessPartner() {
        return businessPartner;
    }

    /**
     * @param businessPartner the businessPartner to set
     */
    public void setBusinessPartner(String businessPartner) {
        this.businessPartner = businessPartner;
    }

    /**
     * @return the businessPartnerIdentifier
     */
    public String getBusinessPartnerIdentifier() {
        return businessPartnerIdentifier;
    }

    /**
     * @param businessPartnerIdentifier the businessPartnerIdentifier to set
     */
    public void setBusinessPartnerIdentifier(String businessPartnerIdentifier) {
        this.businessPartnerIdentifier = businessPartnerIdentifier;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
