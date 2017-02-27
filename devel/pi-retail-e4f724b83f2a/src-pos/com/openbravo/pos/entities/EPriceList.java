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
public class EPriceList extends EBean {

    private String name = null;
    private String currency = null;
    private String currencyIdentifier;
    private boolean taxesIncluded;

    public static ClassEBean<EPriceList> getClassEBean() {
        return new ClassEBean<EPriceList>() {
            @Override
            public EPriceList create() {
                return new EPriceList();
            }

            @Override
            public String getEntity() {
                return "PricingPriceList";
            }
        };
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(JSONFormats.STRING.readJSON(json.opt("name")));
        setCurrency(JSONFormats.STRING.readJSON(json.opt("currency")));
        setCurrencyIdentifier(JSONFormats.STRING.readJSON(json.opt("currency._identifier")));
        setTaxesIncluded(json.optBoolean("priceIncludesTax"));
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
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the currencyIdentifier
     */
    public String getCurrencyIdentifier() {
        return currencyIdentifier;
    }

    /**
     * @param currencyIdentifier the currencyIdentifier to set
     */
    public void setCurrencyIdentifier(String currencyIdentifier) {
        this.currencyIdentifier = currencyIdentifier;
    }

    /**
     * @return the taxesIncluded
     */
    public boolean isTaxesIncluded() {
        return taxesIncluded;
    }

    /**
     * @param taxesIncluded the taxesIncluded to set
     */
    public void setTaxesIncluded(boolean taxesIncluded) {
        this.taxesIncluded = taxesIncluded;
    }
}
