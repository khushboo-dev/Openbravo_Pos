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
public class EPaymentSched extends EBean {

    private String salesOrder;
    private String invoice;

    private String currency;
    private String currencyIdentifier;
    private double expected;
    private double received;
    private double outstanding;

    private String paymentMethod;
    private String paymentMethodIdentifier;


    @Override
    public void writeJSON(JSONObject json) throws JSONException {
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setSalesOrder(JSONFormats.STRING.readJSON(json.opt("salesOrder")));
        setInvoice(JSONFormats.STRING.readJSON(json.opt("invoice")));
        setCurrency(JSONFormats.STRING.readJSON(json.opt("currency")));
        setCurrencyIdentifier(JSONFormats.STRING.readJSON(json.opt("currency._identifier")));
        setExpected(JSONFormats.DOUBLE.readJSON(json.opt("expected")));
        setReceived(JSONFormats.DOUBLE.readJSON(json.opt("received")));
        setOutstanding(JSONFormats.DOUBLE.readJSON(json.opt("outstanding")));
        setPaymentMethod(JSONFormats.STRING.readJSON(json.opt("paymentMethod")));
        setPaymentMethodIdentifier(JSONFormats.STRING.readJSON(json.opt("paymentMethod._identifier")));
    }

    public static ClassEBean<ETaxCategory> getClassEBean() {
        return new ClassEBean<ETaxCategory>() {
            @Override
            public ETaxCategory create() {
                return new ETaxCategory();
            }

            @Override
            public String getEntity() {
                return "FIN_Payment_Sched_Ord_V";
            }
        };
    }

    /**
     * @return the salesOrder
     */
    public String getSalesOrder() {
        return salesOrder;
    }

    /**
     * @param salesOrder the salesOrder to set
     */
    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    /**
     * @return the invoice
     */
    public String getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(String invoice) {
        this.invoice = invoice;
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
     * @return the expected
     */
    public double getExpected() {
        return expected;
    }

    /**
     * @param expected the expected to set
     */
    public void setExpected(double expected) {
        this.expected = expected;
    }

    /**
     * @return the received
     */
    public double getReceived() {
        return received;
    }

    /**
     * @param received the received to set
     */
    public void setReceived(double received) {
        this.received = received;
    }

    /**
     * @return the outstanding
     */
    public double getOutstanding() {
        return outstanding;
    }

    /**
     * @param outstanding the outstanding to set
     */
    public void setOutstanding(double outstanding) {
        this.outstanding = outstanding;
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
     * @return the paymentMethodIdentifier
     */
    public String getPaymentMethodIdentifier() {
        return paymentMethodIdentifier;
    }

    /**
     * @param paymentMethodIdentifier the paymentMethodIdentifier to set
     */
    public void setPaymentMethodIdentifier(String paymentMethodIdentifier) {
        this.paymentMethodIdentifier = paymentMethodIdentifier;
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

}
