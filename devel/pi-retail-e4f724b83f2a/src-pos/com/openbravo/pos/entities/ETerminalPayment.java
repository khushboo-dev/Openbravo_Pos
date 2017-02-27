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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class ETerminalPayment extends EBean {
    
    private String searchKey;
    private String documentType;
    private String documentTypeIdentifier;
    private String financialAccount;
    private String financialAccountIdentifier;
    private String paymentMethod;
    private String paymentMethodIdentifier;
    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        // Not needed
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setSearchKey(json.getString("searchKey"));
        setDocumentType(json.getString("documentType"));
        setDocumentTypeIdentifier(json.getString("documentType._identifier"));
        setFinancialAccount(json.getString("financialAccount"));
        setFinancialAccountIdentifier(json.getString("financialAccount._identifier"));
        setPaymentMethod(json.getString("paymentMethod"));
        setPaymentMethodIdentifier(json.getString("paymentMethod._identifier"));
    }

    public static ClassEBean<ETerminalPayment> getClassEBean() {
        return new ClassEBean<ETerminalPayment>() {
            @Override
            public ETerminalPayment create() {
                return new ETerminalPayment();
            }

            @Override
            public String getEntity() {
                return "OBPOS_App_Payment";
            }
        };
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
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the documentTypeIdentifier
     */
    public String getDocumentTypeIdentifier() {
        return documentTypeIdentifier;
    }

    /**
     * @param documentTypeIdentifier the documentTypeIdentifier to set
     */
    public void setDocumentTypeIdentifier(String documentTypeIdentifier) {
        this.documentTypeIdentifier = documentTypeIdentifier;
    }

    /**
     * @return the financialAccount
     */
    public String getFinancialAccount() {
        return financialAccount;
    }

    /**
     * @param financialAccount the financialAccount to set
     */
    public void setFinancialAccount(String financialAccount) {
        this.financialAccount = financialAccount;
    }

    /**
     * @return the financialAccountIdentifier
     */
    public String getFinancialAccountIdentifier() {
        return financialAccountIdentifier;
    }

    /**
     * @param financialAccountIdentifier the financialAccountIdentifier to set
     */
    public void setFinancialAccountIdentifier(String financialAccountIdentifier) {
        this.financialAccountIdentifier = financialAccountIdentifier;
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

}
