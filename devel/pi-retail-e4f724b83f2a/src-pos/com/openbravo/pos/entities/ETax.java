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
public class ETax extends EBean {

    private String name = null;
    private String taxCategory = null;
    private Date validFrom = null;
    private String bpTaxCategory = null;
    private String parentTax = null;
    private double rate = 0.0;
    private boolean cascade;
    private int line = 0;


    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("taxCategory", JSONFormats.STRING.writeJSON(getTaxCategory()));
        json.put("validFromDate", JSONFormats.TIMESTAMP.writeJSON(getValidFrom()));
        json.put("businessPartnerTaxCategory", JSONFormats.STRING.writeJSON(getBPTaxCategory()));
        json.put("parentTaxRate", JSONFormats.STRING.writeJSON(getParentTax()));
        json.put("rate", getRate());
        json.put("cascade", isCascade());
        json.put("lineNo", getLine());
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setTaxCategory(JSONFormats.STRING.readJSON(json.opt("taxCategory")));
        setValidFrom(JSONFormats.TIMESTAMP.readJSON(json.opt("validFromDate")));
        setBPTaxCategory(JSONFormats.STRING.readJSON(json.opt("businessPartnerTaxCategory")));
        setParentTax(JSONFormats.STRING.readJSON(json.opt("parentTaxRate")));
        setRate(json.optDouble("rate"));
        setCascade(json.optBoolean("cascade"));
        setLine(json.optInt("lineNo"));
    }

    public static ClassEBean<ETax> getClassEBean() {
        return new ClassEBean<ETax>() {
            @Override
            public ETax create() {
                return new ETax();
            }

            @Override
            public String getEntity() {
                return "FinancialMgmtTaxRate";
            }
        };
    }

    @Override
    public String toString(){
        return getName();
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
     * @return the taxcategory
     */
    public String getTaxCategory() {
        return taxCategory;
    }

    /**
     * @param taxcategory the taxcategory to set
     */
    public void setTaxCategory(String taxcategory) {
        this.taxCategory = taxcategory;
    }

    /**
     * @return the validfrom
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * @param validfrom the validfrom to set
     */
    public void setValidFrom(Date validfrom) {
        this.validFrom = validfrom;
    }

    /**
     * @return the bptaxcategory
     */
    public String getBPTaxCategory() {
        return bpTaxCategory;
    }

    /**
     * @param bptaxcategory the bptaxcategory to set
     */
    public void setBPTaxCategory(String bptaxcategory) {
        this.bpTaxCategory = bptaxcategory;
    }

    /**
     * @return the parenttax
     */
    public String getParentTax() {
        return parentTax;
    }

    /**
     * @param parenttax the parenttax to set
     */
    public void setParentTax(String parenttax) {
        this.parentTax = parenttax;
    }

    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * @return the cascade
     */
    public boolean isCascade() {
        return cascade;
    }

    /**
     * @param cascade the cascade to set
     */
    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }

    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(int line) {
        this.line = line;
    }

}
