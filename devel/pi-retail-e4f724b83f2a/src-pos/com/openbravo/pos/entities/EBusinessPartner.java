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
import com.openbravo.pos.util.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class EBusinessPartner extends EBean {

    private String searchKey;
    private String name;
    private String name2;
    private String taxID;
    private String description;   
    private boolean customer;
    private double creditLimit;
    private double creditUsed;
    private String salesTaxCategory;
    private String paymentMethod;
    private String paymentTerms;
    private String invoiceTerms; // I Immediate,// D Do not invoice

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("searchKey", getSearchKey());
        json.put("name", getName());
        json.put("name2", JSONFormats.STRING.writeJSON(getName2()));
        json.put("taxID", JSONFormats.STRING.writeJSON(getTaxID()));
        json.put("description", JSONFormats.STRING.writeJSON(getDescription()));
        // json.put("customer", isCustomer());
        // json.put("creditLimit", getCreditLimit());
        // json.put("creditUsed", getCreditUsed());
        // json.put("sOBPTaxCategory", JSONFormats.STRING.writeJSON(getSalesTaxCategory()));

    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setSearchKey(json.optString("searchKey"));
        setName(json.optString("name"));
        setName2(JSONFormats.STRING.readJSON(json.opt("name2")));
        setTaxID(JSONFormats.STRING.readJSON(json.opt("taxID")));
        setDescription(JSONFormats.STRING.readJSON(json.opt("description")));
        setCustomer(json.optBoolean("customer"));
        setCreditLimit(json.optDouble("creditLimit"));
        setCreditUsed(json.optDouble("creditUsed"));
        setSalesTaxCategory(JSONFormats.STRING.readJSON(json.opt("sOBPTaxCategory")));
        setPaymentMethod(JSONFormats.STRING.readJSON(json.opt("paymentMethod")));
        setPaymentTerms(JSONFormats.STRING.readJSON(json.opt("paymentTerms")));
        setInvoiceTerms(JSONFormats.STRING.readJSON(json.opt("invoiceTerms")));
    }

    public static ClassEBean<EBusinessPartner> getClassEBean() {
        return new ClassEBean<EBusinessPartner>() {
            @Override
            public EBusinessPartner create() {
                return new EBusinessPartner();
            }

            @Override
            public String getEntity() {
                return "BusinessPartner";
            }
        };
    }

    @Override
    public String toString() {
        return getName();
    }

    public String printTaxid() {
        return StringUtils.encodeXML(taxID);
    }

    public String printName() {
        return StringUtils.encodeXML(name);
    }

    /**
     * @return the searchKey
     */
    public String getSearchKey() {
        return searchKey;
    }

    /**
     * @param searchKey the searchKey to set
     */
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
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
     * @return the name2
     */
    public String getName2() {
        return name2;
    }

    /**
     * @param name2 the name2 to set
     */
    public void setName2(String name2) {
        this.name2 = name2;
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
     * @return the customer
     */
    public boolean isCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    /**
     * @return the taxID
     */
    public String getTaxID() {
        return taxID;
    }

    /**
     * @param taxID the taxID to set
     */
    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    /**
     * @return the creditLimit
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * @param creditLimit the creditLimit to set
     */
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * @return the creditUsed
     */
    public double getCreditUsed() {
        return creditUsed;
    }

    /**
     * @param creditUsed the creditUsed to set
     */
    public void setCreditUsed(double creditUsed) {
        this.creditUsed = creditUsed;
    }

    /**
     * @return the salesTaxCategory
     */
    public String getSalesTaxCategory() {
        return salesTaxCategory;
    }

    /**
     * @param salesTaxCategory the salesTaxCategory to set
     */
    public void setSalesTaxCategory(String salesTaxCategory) {
        this.salesTaxCategory = salesTaxCategory;
    }

    /**
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * @return the paymentTerms
     */
    public String getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * @param paymentTerms the paymentTerms to set
     */
    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    /**
     * @return the invoiceTerms
     */
    public String getInvoiceTerms() {
        return invoiceTerms;
    }

    /**
     * @param invoiceTerms the invoiceTerms to set
     */
    public void setInvoiceTerms(String invoiceTerms) {
        this.invoiceTerms = invoiceTerms;
    }
}
