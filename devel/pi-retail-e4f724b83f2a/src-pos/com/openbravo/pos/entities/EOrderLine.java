//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
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

import com.openbravo.basic.BasicException;
import com.openbravo.basic.FatalException;
import com.openbravo.data.json.ClassEBean;
import com.openbravo.data.json.EBean;
import com.openbravo.data.json.JSONFormats;
import com.openbravo.data.json.ServiceFind;
import com.openbravo.pos.util.StringUtils;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.App;
import com.openbravo.pos.forms.DataLogicSales;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrianromero
 */
public class EOrderLine extends EBean {

    private static final long serialVersionUID = 6608012948284450199L;
    private static final Logger logger = Logger.getLogger(EOrderLine.class.getName());

    transient
    private EOrder orderEntity = null;
    private int line = 0;

    private double multiply;
    private double price;
    private ETax tax;
    private String attsetinstid;

    private String product = null;
    private String productIdentifier = null;
    transient
    private EProduct productEntity = null;


    

    private Properties attributes;

    /** Creates new TicketLineInfo */
    public EOrderLine(String product, String productIdentifier, double dMultiply, double dPrice, ETax tax, Properties props) {
        init(product, productIdentifier, null, dMultiply, dPrice, tax, props);
    }

    public EOrderLine(String product, String productIdentifier, double dMultiply, double dPrice, ETax tax) {
        init(product, productIdentifier, null, dMultiply, dPrice, tax, new Properties());
    }

    public EOrderLine(String product, String productIdentifier, String producttaxcategory, double dMultiply, double dPrice, ETax tax) {

        init(product, productIdentifier, null, dMultiply, dPrice, tax, new Properties());
    }

    public EOrderLine() {
        init(null, null, 0.0, 0.0, null, new Properties());
    }

    public EOrderLine(EProduct product, double dMultiply, double dPrice, ETax tax, Properties attributes) {
        init(product, null, dMultiply, dPrice, tax, attributes);
    }

    public EOrderLine(EProduct product, double dPrice, ETax tax, Properties attributes) {
        init(product, null, 1.0, dPrice, tax, attributes);
    }

    public EOrderLine(EOrderLine line) {
        init(line.product, line.productIdentifier, line.attsetinstid, line.multiply, line.price, line.tax, (Properties) line.attributes.clone());
    }

    private void init(String product, String productIdentifier, String attsetinstid, double dMultiply, double dPrice, ETax tax, Properties attributes) {

        setProduct(product, productIdentifier);
        this.attsetinstid = attsetinstid;
        this.multiply = dMultiply;
        this.price = dPrice;
        this.tax = tax;
        this.attributes = attributes;
        // m_sTicket = null;
        line = 0;
    }
    private void init(EProduct productEntity, String attsetinstid, double dMultiply, double dPrice, ETax tax, Properties attributes) {

        setProductEntity(productEntity);
        this.attsetinstid = attsetinstid;
        this.multiply = dMultiply;
        this.price = dPrice;
        this.tax = tax;
        this.attributes = attributes;
        // m_sTicket = null;
        line = 0;
    }

    @Override
    public void writeJSON(JSONObject json) throws JSONException {

        json.put("salesOrder", JSONFormats.STRING.writeJSON(getOrderEntity().getId()));
        json.put("orderDate",  JSONFormats.DATE.writeJSON(getOrderEntity().getOrderDate())); // No time will be saved...
        json.put("warehouse", App.getInstance().getTerminal().getWarehouse());
        json.put("businessPartner", getOrderEntity().getCustomer());
        json.put("partnerAddress", getOrderEntity().getCustomerLocationEntity().getId());
        json.put("currency", App.getInstance().getPriceList().getCurrency());

        json.put("lineNo", getLine());
        json.put("product", JSONFormats.STRING.writeJSON(getProduct()));
        json.put("uOM", getProductEntity().getUOM());
        json.put("unitPrice", getPrice());
        json.put("orderedQuantity", getMultiply());
        json.put("tax", tax.getId());
        json.put("attributeSetValue", attsetinstid);






//        json.put("documentType", getDocType());
//        json.put("transactionDocument", getDocType());
//        json.put("documentNo", JSONFormats.STRING.writeJSON(getDocumentNo()));
//        json.put("orderDate", JSONFormats.TIMESTAMP.writeJSON(getOrderDate())); // No time will be saved...
//        json.put("accountingDate", JSONFormats.TIMESTAMP.writeJSON(getOrderDate())); // No time will be saved...
//        json.put("businessPartner", getCustomer());
//
//        json.put("partnerAddress", getCustomerLocationEntity().getId());
//        json.put("invoiceAddress", getCustomerLocationEntity().getId());
//        json.put("priceList", App.getInstance().getTerminal().getPriceList());
//        json.put("currency", App.getInstance().getPriceList().getCurrency());
//        json.put("scheduledDeliveryDate", JSONFormats.TIMESTAMP.writeJSON(getOrderDate())); // No time will be saved...
//        json.put("warehouse", App.getInstance().getTerminal().getWarehouse());
//        json.put("paymentMethod", getCustomerEntity().getPaymentMethod()); // business partner Payment terms.
//        json.put("paymentTerms", getCustomerEntity().getPaymentTerms()); // business partner Payment terms.
//        json.put("invoiceTerms", getCustomerEntity().getInvoiceTerms()); // business partner invoice terms
//
//        json.put("salesTransaction", true);
//        json.put("documentStatus", "DR");
//        json.put("documentAction", "CO");
//        json.put("processNow", false);

    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
    }

    public static ClassEBean<EOrderLine> getClassEBean() {
        return new ClassEBean<EOrderLine>() {
            @Override
            public EOrderLine create() {
                return new EOrderLine();
            }

            @Override
            public String getEntity() {
                return "OrderLine";
            }

            @Override
            public EOrderLine refreshFromJSON(EOrderLine obj, JSONObject json) throws JSONException {
                ClassEBean.copyFromJSON(obj, json);
                // obj.setDocumentNo(JSONFormats.STRING.readJSON(json.opt("documentNo")));
                return obj;
            }
        };
    }

    public EOrder getOrderEntity() {
        return orderEntity;
    }

//    public void setOrderEntity(EOrder orderEntity) {
//        this.orderEntity = orderEntity;
//    }

    public int getLine() {
        return line;
    }

    public void setOrderEntity(EOrder orderEntity, int line) {
        this.orderEntity = orderEntity;
        this.line = line;
    }

    public final void setProduct(String product, String productIdentifier) {

        if (product == null) {
            // assign the default
            this.product = App.getInstance().getProduct().getId();
            this.productIdentifier = App.getInstance().getProduct().getIdentifier();
            this.productEntity = App.getInstance().getProduct();
        } else {
            this.product = product;
            this.productIdentifier = productIdentifier;
            this.productEntity = null;
        }
    }

    public final String getProduct() {
        return product;
    }

    public final String getProductIdentifier() {
        return productIdentifier;
    }

    public final void setProductEntity(EProduct productEntity) {
        if (productEntity == null) {
            // assign the default
            this.product = App.getInstance().getProduct().getId();
            this.productIdentifier = App.getInstance().getProduct().getIdentifier();
            this.productEntity = App.getInstance().getProduct();
        } else {
            this.product = productEntity.getId();
            this.productIdentifier = productEntity.getIdentifier();
            this.productEntity = productEntity;
        }       
    }

    public final EProduct getProductEntity() {
        if (productEntity == null && product != null) {
            try {
                productEntity = ((DataLogicSales) App.getInstance().getBean("com.openbravo.pos.forms.DataLogicSales")).
                        getProductInfoById(App.getInstance().getPriceListVersion(), product);
            } catch (BasicException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new FatalException(ex);
            }
        }
        return productEntity;
    }

    public double getMultiply() {
        return multiply;
    }

    public void setMultiply(double dValue) {
        multiply = dValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ETax getTax() {
        return tax;
    }

    public void setTax(ETax tax) {
        this.tax = tax;
    }

    public double getPriceTax() {
        return price * (1.0 + getTaxRate() / 100.0);
    }

    public void setPriceTax(double dValue) {
        price = dValue / (1.0 + getTaxRate() / 100.0);
    }

    public boolean isProductCom() {
        return getProductEntity().isAuxiliar();
    }

    public String getProductTaxCategoryID() {
        return getProductEntity().getTaxCategory();
    }

    public String getProductCategoryID() {
        return getProductEntity().getPosCategory();
    }

    public String getProductAttSetId() {
        return getProductEntity().getAttributeSet();
    }

    public double getTaxRate() {
        return tax == null ? 0.0 : tax.getRate();
    }

    public double getSubValue() {
        return price * multiply;
    }

    public double getTaxLine() {
        return price * multiply * getTaxRate() / 100.0;
    }

    public double getValue() {
        return price * multiply * (1.0 + getTaxRate() / 100.0);
    }

    public String printName() {
        return StringUtils.encodeXML(productIdentifier);
    }

    public String printMultiply() {
        return Formats.DOUBLE.formatValue(multiply);
    }

    public String printPrice() {
        return Formats.CURRENCY.formatValue(getPrice());
    }

    public String printPriceTax() {
        return Formats.CURRENCY.formatValue(getPriceTax());
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(getTax());
    }

    public String printTaxRate() {
        return Formats.PERCENT.formatValue(getTaxRate() / 100.0);
    }

    public String printSubValue() {
        return Formats.CURRENCY.formatValue(getSubValue());
    }

    public String printValue() {
        return Formats.CURRENCY.formatValue(getValue());
    }







    public EOrderLine copyTicketLine() {
        EOrderLine l = new EOrderLine();
        // l.m_sTicket = null;
        // l.m_iLine = -1;
        l.product = product;
        l.productIdentifier = productIdentifier;
        l.productEntity = productEntity;

        l.attsetinstid = attsetinstid;
        l.multiply = multiply;
        l.price = price;
        l.tax = tax;
        l.attributes = (Properties) attributes.clone();
        return l;
    }

    public String getProductAttSetInstDesc() {
        return attributes.getProperty("product.attsetdesc", "");
    }

    public void setProductAttSetInstDesc(String value) {
        if (value == null) {
            attributes.remove(value);
        } else {
            attributes.setProperty("product.attsetdesc", value);
        }
    }

    public String getProductAttSetInstId() {
        return attsetinstid;
    }

    public void setProductAttSetInstId(String value) {
        attsetinstid = value;
    }

    public String getProperty(String key) {
        return attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        attributes.setProperty(key, value);
    }

    public Properties getProperties() {
        return attributes;
    }
}
