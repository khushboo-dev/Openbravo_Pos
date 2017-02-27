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

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.openbravo.format.Formats;
import com.openbravo.basic.BasicException;
import com.openbravo.basic.FatalException;
import com.openbravo.data.json.ClassEBean;
import com.openbravo.data.json.EBean;
import com.openbravo.data.json.JSONFormats;
import com.openbravo.data.json.ServiceFind;
import com.openbravo.data.loader.LocalRes;
import com.openbravo.pos.forms.App;
import com.openbravo.pos.forms.DataLogicSales;
import com.openbravo.pos.ticket.UserInfo;
import com.openbravo.pos.util.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrianromero
 */
public class EOrder extends EBean {

    private static final long serialVersionUID = 2765650092387265178L;
    private static final Logger logger = Logger.getLogger(EOrder.class.getName());

    public static final int RECEIPT_NORMAL = 0;
    public static final int RECEIPT_REFUND = 1;
    public static final int RECEIPT_PAYMENT = 2;

    private static DateFormat m_dateformat = new SimpleDateFormat("hh:mm");

    // new Order fields
    private String docType;
    private String documentNo;
    private java.util.Date orderDate= new Date(); // It is supposed to be a date. no hours.
    private String customer = null;
    private String customerIdentifier = null;
    transient
    private EBusinessPartner customerEntity = null;
    transient
    private EBusinessPartnerLocation customerLocationEntity = null;
    private List<EOrderLine> m_aLines = new ArrayList<EOrderLine>();

    // Not persistent fields
    transient
    private List<XOrderPayment> payments = new ArrayList<XOrderPayment>();

    // Old Order fields;
    private int tickettype = RECEIPT_NORMAL;
    private Properties attributes = new Properties();
    private UserInfo m_User = null;


    private List<TicketTaxInfo> taxes = null;



    public EOrder() {
        setCustomer(null, null);
        docType = App.getInstance().getTerminal().getDocumentType();
    }

    @Override
    public void writeJSON(JSONObject json) throws JSONException {

        json.put("documentType", getDocType());
        json.put("transactionDocument", getDocType());
        json.put("documentNo", JSONFormats.STRING.writeJSON(getDocumentNo()));
        json.put("orderDate", JSONFormats.DATE.writeJSON(getOrderDate())); // No time will be saved...
        json.put("accountingDate", JSONFormats.DATE.writeJSON(getOrderDate())); // No time will be saved...
        json.put("businessPartner", getCustomer());
        json.put("partnerAddress", getCustomerLocationEntity().getId());
        json.put("invoiceAddress", getCustomerLocationEntity().getId());
        json.put("priceList", App.getInstance().getTerminal().getPriceList());
        json.put("currency", App.getInstance().getPriceList().getCurrency());
        json.put("scheduledDeliveryDate", JSONFormats.DATE.writeJSON(getOrderDate())); // No time will be saved...
        json.put("warehouse", App.getInstance().getTerminal().getWarehouse());
        json.put("paymentMethod", getCustomerEntity().getPaymentMethod()); // business partner Payment terms.
        json.put("paymentTerms", getCustomerEntity().getPaymentTerms()); // business partner Payment terms.
        json.put("invoiceTerms", getCustomerEntity().getInvoiceTerms()); // business partner invoice terms

        json.put("salesTransaction", true);
        json.put("documentStatus", "DR");
        json.put("documentAction", "CO");
        json.put("processNow", false);

//        json.put("name", getName());
//        json.put("name2", JSONFormats.STRING.writeJSON(getName2()));
//        json.put("taxID", JSONFormats.STRING.writeJSON(getTaxID()));
//        json.put("description", JSONFormats.STRING.writeJSON(getDescription()));
//        // json.put("customer", isCustomer());
//        // json.put("creditLimit", getCreditLimit());
//        // json.put("creditUsed", getCreditUsed());
//        // json.put("sOBPTaxCategory", JSONFormats.STRING.writeJSON(getSalesTaxCategory()));

    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
//        setSearchKey(json.optString("searchKey"));
//        setName(json.optString("name"));
//        setName2(JSONFormats.STRING.readJSON(json.opt("name2")));
//        setTaxID(JSONFormats.STRING.readJSON(json.opt("taxID")));
//        setDescription(JSONFormats.STRING.readJSON(json.opt("description")));
//        setCustomer(json.optBoolean("customer"));
//        setCreditLimit(json.optDouble("creditLimit"));
//        setCreditUsed(json.optDouble("creditUsed"));
//        setSalesTaxCategory(JSONFormats.STRING.readJSON(json.opt("sOBPTaxCategory")));
    }

    public static ClassEBean<EOrder> getClassEBean() {
        return new ClassEBean<EOrder>() {
            @Override
            public EOrder create() {
                return new EOrder();
            }

            @Override
            public String getEntity() {
                return "Order";
            }

            @Override
            public EOrder refreshFromJSON(EOrder obj, JSONObject json) throws JSONException {
                ClassEBean.copyFromJSON(obj, json);
                obj.setDocumentNo(JSONFormats.STRING.readJSON(json.opt("documentNo")));
                return obj;
            }
        };
    }

//    public void readValues(DataRead dr) throws BasicException {
//        m_sId = dr.getString(1);
//        tickettype = dr.getInt(2).intValue();
//        m_iTicketId = dr.getInt(3).intValue();
//        m_dDate = dr.getTimestamp(4);
//        m_sActiveCash = dr.getString(5);
//        try {
//            byte[] img = dr.getBytes(6);
//            if (img != null) {
//                attributes.loadFromXML(new ByteArrayInputStream(img));
//            }
//        } catch (IOException e) {
//        }
//        m_User = new UserInfo(dr.getString(7), dr.getString(8));
//        customer =  dr.getString(9);
//        customerIdentifier =  dr.getString(9);
//        customerEntity = null;
//        locationEntity = null;
//        m_aLines = new ArrayList<TicketLineInfo>();
//
//        payments = new ArrayList<TicketPaymentInfo>();
//        taxes = null;
//    }

    public EOrder copyTicket() {
        EOrder t = new EOrder();

        t.docType = docType;
        t.documentNo = documentNo;

        t.tickettype = tickettype;

        t.orderDate = orderDate;
        t.attributes = (Properties) attributes.clone();
        t.m_User = m_User;
        t.customer = customer;
        t.customerIdentifier = customerIdentifier;
        t.customerEntity = customerEntity;
        t.customerLocationEntity = customerLocationEntity;

        t.m_aLines = new ArrayList<EOrderLine>();
        for (EOrderLine l : m_aLines) {
            t.m_aLines.add(l.copyTicketLine());
        }
        t.refreshLines();



        return t;
    }

    public int getTicketType() {
        return tickettype;
    }

    public void setTicketType(int tickettype) {
        this.tickettype = tickettype;
    }

    public String getName(Object info) {

        StringBuilder name = new StringBuilder();

        if (customerIdentifier != null) {
            name.append(customerIdentifier);
            name.append(" - ");
        }

        if (info == null) {
            if (documentNo == null) {
                name.append("(");
                name.append(m_dateformat.format(orderDate));
                name.append(" ");
                name.append(Long.toString(orderDate.getTime() % 1000));
                name.append(")");
            } else {
                name.append(documentNo);
            }
        } else {
            name.append(info.toString());
        }
        
        return name.toString();
    }

    public String getName() {
        return getName(null);
    }

    public java.util.Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(java.util.Date dDate) {
        orderDate = dDate;
    }

    public UserInfo getUser() {
        return m_User;
    }

    public void setUser(UserInfo value) {
        m_User = value;
    }

    public final void setCustomer(String customer, String customerIdentifier) {

        if (customer == null) {
            // assign the default
            this.customer = App.getInstance().getBusinessPartner().getId();
            this.customerIdentifier = App.getInstance().getBusinessPartner().getIdentifier();
            this.customerEntity = App.getInstance().getBusinessPartner();
            this.customerLocationEntity = App.getInstance().getBusinessPartnerLocation();
        } else {
            this.customer = customer;
            this.customerIdentifier = customerIdentifier;
            this.customerEntity = null;
            this.customerLocationEntity = null;
        }
    }

    public final String getCustomer() {
        return customer;
    }
 
    public final String getCustomerIdentifier() {
        return customerIdentifier;
    }

    public final EBusinessPartner getCustomerEntity() {
        if (customerEntity == null && customer != null) {
            try {
                customerEntity = new ServiceFind<EBusinessPartner>(App.getInstance().getUser().getHttpSession(), EBusinessPartner.getClassEBean()).find(customer);
            } catch (BasicException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new FatalException(ex);
            }
        }
        return customerEntity;
    }
    
    public final EBusinessPartnerLocation getCustomerLocationEntity() {
        if (customerLocationEntity == null && customer != null) {
            try {
                customerLocationEntity = ((DataLogicSales) App.getInstance().getBean("com.openbravo.pos.forms.DataLogicSales")).
                        findBusinessPartnerLocation(customer);
            } catch (BasicException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new FatalException(ex);
            }
        }
        return customerLocationEntity;
    }
    
    public String getReturnMessage(){
        return ( (getPayments().get(getPayments().size()-1)) instanceof XOrderPaymentCard )
            ? ((XOrderPaymentCard)(getPayments().get(getPayments().size()-1))).getReturnMessage()
            : LocalRes.getIntString("button.ok");
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

    public EOrderLine getLine(int index) {
        return m_aLines.get(index);
    }

    public void addLine(EOrderLine oLine) {

        oLine.setOrderEntity(this, m_aLines.size());
        m_aLines.add(oLine);
    }

    public void insertLine(int index, EOrderLine oLine) {
        m_aLines.add(index, oLine);
        refreshLines();
    }

    public void setLine(int index, EOrderLine oLine) {
        oLine.setOrderEntity(this, index);
        m_aLines.set(index, oLine);
    }

    public void removeLine(int index) {
        m_aLines.remove(index);
        refreshLines();
    }

    public void refreshLines() {
        for (int i = 0; i < m_aLines.size(); i++) {
            getLine(i).setOrderEntity(this, i);
        }
    }

    public int getLinesCount() {
        return m_aLines.size();
    }
    
    public double getArticlesCount() {
        double dArticles = 0.0;
        EOrderLine oLine;

        for (Iterator<EOrderLine> i = m_aLines.iterator(); i.hasNext();) {
            oLine = i.next();
            dArticles += oLine.getMultiply();
        }

        return dArticles;
    }

    public double getSubTotal() {
        double sum = 0.0;
        for (EOrderLine line : m_aLines) {
            sum += line.getSubValue();
        }
        return sum;
    }

    public double getTax() {

        double sum = 0.0;
        if (hasTaxesCalculated()) {
            for (TicketTaxInfo tax : taxes) {
                sum += tax.getTax(); // Taxes are already rounded...
            }
        } else {
            for (EOrderLine line : m_aLines) {
                sum += line.getTaxLine();
            }
        }
        return sum;
    }

    public double getTotal() {
        
        return getSubTotal() + getTax();
    }

    public List<EOrderLine> getLines() {
        return m_aLines;
    }

    public void setLines(List<EOrderLine> l) {
        m_aLines = l;
    }

    public List<XOrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<XOrderPayment> l) {
        payments = l;
        // and refresh
        if (payments != null) {
            for(XOrderPayment p : payments) {
                p.setOrder(this);
            }
        }
    }

    public void resetPayments() {
        // reset link
        if (payments != null) {
            for(XOrderPayment p : payments) {
                p.setOrder(null);
            }
        }
        payments = new ArrayList<XOrderPayment>();
    }

    public List<TicketTaxInfo> getTaxes() {
        return taxes;
    }

    public boolean hasTaxesCalculated() {
        return taxes != null;
    }

    public void setTaxes(List<TicketTaxInfo> l) {
        taxes = l;
    }

    public void resetTaxes() {
        taxes = null;
    }

    public TicketTaxInfo getTaxLine(ETax tax) {

        for (TicketTaxInfo taxline : taxes) {
            if (tax.getId().equals(taxline.getTaxInfo().getId())) {
                return taxline;
            }
        }

        return new TicketTaxInfo(tax);
    }

    public TicketTaxInfo[] getTaxLines() {

        Map<String, TicketTaxInfo> m = new HashMap<String, TicketTaxInfo>();

        EOrderLine oLine;
        for (Iterator<EOrderLine> i = m_aLines.iterator(); i.hasNext();) {
            oLine = i.next();

            TicketTaxInfo t = m.get(oLine.getTax().getId());
            if (t == null) {
                t = new TicketTaxInfo(oLine.getTax());
                m.put(t.getTaxInfo().getId(), t);
            }
            t.add(oLine.getSubValue());
        }

        // return dSuma;       
        Collection<TicketTaxInfo> avalues = m.values();
        return avalues.toArray(new TicketTaxInfo[avalues.size()]);
    }

    public String printDocumentNo() {
        return (documentNo == null) ? "" : documentNo;
    }

    public String printDate() {
        return Formats.DATE.formatValue(orderDate);
    }

    public String printCreatedDate() {
        return Formats.TIMESTAMP.formatValue(this.getCreated());
    }

    public String printUser() {
        return m_User == null ? "" : m_User.getName();
    }

    public String printCustomer() {
        return customerIdentifier;
    }

    public String printArticlesCount() {
        return Formats.DOUBLE.formatValue(new Double(getArticlesCount()));
    }

    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(new Double(getSubTotal()));
    }

    public String printTax() {
        return Formats.CURRENCY.formatValue(new Double(getTax()));
    }

    public String printTotal() {
        return Formats.CURRENCY.formatValue(new Double(getTotal()));
    }

    /**
     * @return the docType
     */
    public String getDocType() {
        return docType;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @return the documentNo
     */
    public String getDocumentNo() {
        return documentNo;
    }

    /**
     * @param documentNo the documentNo to set
     */
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }
}
