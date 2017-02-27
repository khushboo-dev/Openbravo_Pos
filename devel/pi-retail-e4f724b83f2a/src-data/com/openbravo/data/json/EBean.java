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

import com.openbravo.data.loader.IKeyed;
import java.io.Serializable;
import java.util.Date;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public abstract class EBean implements IKeyed, Serializable {

    private static String defaultClient = null;
    private static String defaultOrg = null;

    protected String client;
    protected String org;

    protected String id = null;
    private String identifier = null;

    protected boolean active = true;
    private Date created = null;
    private String createdBy = null;
    private Date updated = null;
    private String updatedBy = null;

    public static void setDefaultClientOrg(String defaultClient, String defaultOrg) {
        EBean.defaultClient = defaultClient;
        EBean.defaultOrg = defaultOrg;
    }

    public EBean() {
        this.client = defaultClient;
        this.org = defaultOrg;
    }

    public void writeJSON(JSONObject json) throws JSONException {
    }

    public void readJSON(JSONObject json) throws JSONException {
    }

    @Override
    public String toString() {
        if (identifier == null) {
            if (id == null) {
                return super.toString();
            } else
                return id;
        } else {
            return identifier;
        }
    }

    /**
     * @return the id
     */
    public final String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public final void setId(String id) {
        this.id = id;
    }

    /**
     * @return the client
     */
    public final String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public final void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the org
     */
    public final String getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public final void setOrg(String org) {
        this.org = org;
    }

    /**
     * @return the active
     */
    public final boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public final void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the created
     */
    public final Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public final void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the createdBy
     */
    public final String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public final void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updated
     */
    public final Date getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public final void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * @return the updatedBy
     */
    public final String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public final void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public final void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
