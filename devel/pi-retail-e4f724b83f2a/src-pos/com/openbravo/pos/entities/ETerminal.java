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
public class ETerminal extends EBean {

    private String clientIdentifier;
    private String orgIdentifier;
    private String searchKey;
    private String name;
    private String version;
    private String warehouse;
    private String warehouseIdentifier;
    private String documentType;
    private String documentTypeIdentifier;
    private String businessPartner;
    private String businessPartnerIdentifier;
    private String priceList;
    private String priceListIdentifier;
    private String product;
    private String productIdentifier;

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        // Not needed
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setClientIdentifier(json.getString("client._identifier"));
        setOrgIdentifier(json.getString("organization._identifier"));
        setSearchKey(json.getString("searchKey"));
        setName(json.getString("name"));
        setVersion(json.getString("version"));
        setWarehouse(json.getString("warehouse"));
        setWarehouseIdentifier(json.getString("warehouse._identifier"));
        setDocumentType(json.getString("documentType"));
        setDocumentTypeIdentifier(json.getString("documentType._identifier"));
        setBusinessPartner(json.getString("businessPartner"));
        setBusinessPartnerIdentifier(json.getString("businessPartner._identifier"));
        setPriceList(json.getString("priceList"));
        setPriceListIdentifier(json.getString("priceList._identifier"));
        setProduct(json.getString("product"));
        setProductIdentifier(json.getString("product._identifier"));
    }

    public static ClassEBean<ETerminal> getClassEBean() {
        return new ClassEBean<ETerminal>() {
            @Override
            public ETerminal create() {
                return new ETerminal();
            }

            @Override
            public String getEntity() {
                return "OBPOS_Applications";
            }
        };
    }

    /**
     * @return the clientIdentifier
     */
    public String getClientIdentifier() {
        return clientIdentifier;
    }

    /**
     * @param clientIdentifier the clientIdentifier to set
     */
    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    /**
     * @return the orgIdentifier
     */
    public String getOrgIdentifier() {
        return orgIdentifier;
    }

    /**
     * @param orgIdentifier the orgIdentifier to set
     */
    public void setOrgIdentifier(String orgIdentifier) {
        this.orgIdentifier = orgIdentifier;
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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the warehouse
     */
    public String getWarehouse() {
        return warehouse;
    }

    /**
     * @param warehouse the warehouse to set
     */
    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * @return the warehouseIdentifier
     */
    public String getWarehouseIdentifier() {
        return warehouseIdentifier;
    }

    /**
     * @param warehouseIdentifier the warehouseIdentifier to set
     */
    public void setWarehouseIdentifier(String warehouseIdentifier) {
        this.warehouseIdentifier = warehouseIdentifier;
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
     * @return the priceList
     */
    public String getPriceList() {
        return priceList;
    }

    /**
     * @param priceList the priceList to set
     */
    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    /**
     * @return the priceListIdentifier
     */
    public String getPriceListIdentifier() {
        return priceListIdentifier;
    }

    /**
     * @param priceListIdentifier the priceListIdentifier to set
     */
    public void setPriceListIdentifier(String priceListIdentifier) {
        this.priceListIdentifier = priceListIdentifier;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the productIdentifier
     */
    public String getProductIdentifier() {
        return productIdentifier;
    }

    /**
     * @param productIdentifier the productIdentifier to set
     */
    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
    }
}
