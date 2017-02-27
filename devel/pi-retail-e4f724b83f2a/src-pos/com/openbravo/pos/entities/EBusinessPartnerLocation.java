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
public class EBusinessPartnerLocation extends EBean {

    private String businessPartner = null;
    private String businessPartnerIdentifier = null;
    private String location = null;
    private String locationIdentifier = null;

    public static ClassEBean<EBusinessPartnerLocation> getClassEBean() {
        return new ClassEBean<EBusinessPartnerLocation>() {
            @Override
            public EBusinessPartnerLocation create() {
                return new EBusinessPartnerLocation();
            }

            @Override
            public String getEntity() {
                return "BusinessPartnerLocation";
            }
        };
    }

    public void writeJSON(JSONObject json) throws JSONException {
        json.put("businessPartner", getBusinessPartner());
        json.put("locationAddress", getLocation());
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setBusinessPartner(JSONFormats.STRING.readJSON(json.opt("businessPartner")));
        setBusinessPartnerIdentifier(JSONFormats.STRING.readJSON(json.opt("businessPartner._identifier")));
        setLocation(JSONFormats.STRING.readJSON(json.opt("locationAddress")));
        setLocationIdentifier(JSONFormats.STRING.readJSON(json.opt("locationAddress._identifier")));
    }

    @Override
    public String toString() {
        return locationIdentifier;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the locationIdentifier
     */
    public String getLocationIdentifier() {
        return locationIdentifier;
    }

    /**
     * @param locationIdentifier the locationIdentifier to set
     */
    public void setLocationIdentifier(String locationIdentifier) {
        this.locationIdentifier = locationIdentifier;
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

}
