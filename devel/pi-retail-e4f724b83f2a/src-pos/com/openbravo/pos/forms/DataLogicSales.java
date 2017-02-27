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

package com.openbravo.pos.forms;

import com.openbravo.pos.entities.EOrder;
import com.openbravo.pos.entities.EOrderLine;
import java.util.Date;
import java.util.List;
import com.openbravo.pos.util.StringUtils;
import com.openbravo.data.loader.*;
import com.openbravo.format.Formats;
import com.openbravo.basic.BasicException;
import com.openbravo.data.json.ResultProcess;
import com.openbravo.data.json.ServiceFind;
import com.openbravo.data.json.ServiceHQL;
import com.openbravo.data.json.ServiceProcedure;
import com.openbravo.data.json.ServiceProcess;
import com.openbravo.data.json.ServicePut;
import com.openbravo.data.json.ServiceQBF;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.Row;
import com.openbravo.pos.entities.EBusinessPartner;
import com.openbravo.pos.entities.EBusinessPartnerLocation;
import com.openbravo.pos.entities.ECategory;
import com.openbravo.pos.entities.EFloorView;
import com.openbravo.pos.entities.EWarehouse;
import com.openbravo.pos.entities.EProduct;
import com.openbravo.pos.entities.ETax;
import com.openbravo.pos.entities.ETaxCategory;
import com.openbravo.pos.inventory.AttributeSetInfo;
import com.openbravo.pos.inventory.TaxCustCategoryInfo;
import com.openbravo.pos.inventory.MovementReason;
import com.openbravo.pos.entities.XOrderPayment;
import com.openbravo.pos.entities.XOrderPaymentVoucher;
import com.openbravo.pos.ticket.FindTicketsInfo;
import com.openbravo.pos.entities.TicketTaxInfo;

/**
 *
 * @author adrianromero
 */
public class DataLogicSales extends BeanFactoryDataSingle {

    protected Session s;

    protected Datas[] auxiliarDatas;
    protected Datas[] stockdiaryDatas;
    // protected Datas[] productcatDatas;
    protected Datas[] paymenttabledatas;
    protected Datas[] stockdatas;

    protected Row productsRow;

    /** Creates a new instance of SentenceContainerGeneric */
    public DataLogicSales() {
        stockdiaryDatas = new Datas[] {Datas.STRING, Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE};
        paymenttabledatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE};
        stockdatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE};
        auxiliarDatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING};

        productsRow = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true),
                new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true),
                new Field("ISCOM", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISSCALE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field(AppLocal.getIntString("label.prodpricebuy"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodpricesell"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.taxcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.attributeset"), Datas.STRING, Formats.STRING, false, false, true),
                new Field("IMAGE", Datas.IMAGE, Formats.NULL),
                new Field("STOCKCOST", Datas.DOUBLE, Formats.CURRENCY),
                new Field("STOCKVOLUME", Datas.DOUBLE, Formats.DOUBLE),
                new Field("ISCATALOG", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("CATORDER", Datas.INT, Formats.INT),
                new Field("PROPERTIES", Datas.BYTES, Formats.NULL));
    }

    public void init(Session s){
        this.s = s;
    }

    public final Row getProductsRow() {
        return productsRow;
    }

    // Utility functions for products.
    public final EProduct getProductInfoById(String pricelistversion, String id) throws BasicException {
        return new ServiceHQL<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(), "where priceListVersion.id = :parameter0 and id = :parameter1").find(new String[]{pricelistversion, id});
    }
    public final EProduct getProductInfoByCode(String pricelistversion, String code) throws BasicException {
        return new ServiceHQL<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(), "where priceListVersion.id = :parameter0 and uPCEAN = :parameter1").find(new String[]{pricelistversion, code});
    }
    public final EProduct getProductInfoByReference(String pricelistversion, String reference) throws BasicException {
        return new ServiceHQL<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(), "where priceListVersion.id = :parameter0 and searchKey = :parameter1").find(new String[]{pricelistversion, reference});
    }

    // Catalogo de productos
    public final List<ECategory> getRootCategories() throws BasicException {
        return new ServiceHQL<ECategory>(App.getInstance().getUser().getHttpSession(), ECategory.getClassEBean(), "where parentPOSCategory = null order by name").list();
    }
    public final List<ECategory> getSubcategories(String category) throws BasicException  {
        return new ServiceHQL<ECategory>(App.getInstance().getUser().getHttpSession(), ECategory.getClassEBean(), "where parentPOSCategory.id = :parameter0 order by name").list(category);
    }

    public List<EProduct> getProductCatalog(String pricelistversion, String category) throws BasicException  {
        return new ServiceHQL<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(),
                "where priceListVersion.id = :parameter0 and pOSCategory.id = :parameter1 and isCatalog = true order by pOSLine, name").
                list(new String[]{pricelistversion, category});
    }

    public List<EProduct> getProductComments(String id) throws BasicException {
        return new ServiceHQL<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(), "where auxiliaryProductOf.id = :parameter0 order by pOSLine, name").list(id);  // filtrado por los relacionados
    }
  
    // Products list
    public final ServiceQBF<EProduct> getProductList() {
        return new ServiceQBF<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(),
                "where priceListVersion.id = :priceListVersion and $qbffilter order by searchKey");
    }
    
    // Products list
    public ServiceQBF<EProduct> getProductListNormal() {
        return new ServiceQBF<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(),
                "where priceListVersion.id = :priceListVersion and auxiliaryProductOf is null and $qbffilter order by searchKey");
    }
    
    //Auxiliar list for a filter
    public ServiceQBF<EProduct> getProductListAuxiliar() {
        return new ServiceQBF<EProduct>(App.getInstance().getUser().getHttpSession(), EProduct.getClassEBean(),
                "where priceListVersion.id = :priceListVersion and auxiliaryProductOf is not null and $qbffilter order by searchKey");
    }
    
    //Tickets and Receipt list
    public SentenceList getTicketsList() {
         return new StaticSentence(s
            , new QBFBuilder(
            "SELECT T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME, SUM(PM.TOTAL) "+ 
            "FROM OBPOS_RECEIPTS R JOIN OBPOS_TICKETS T ON R.OBPOS_RECEIPTS_ID = T.OBPOS_TICKETS_ID LEFT OUTER JOIN OBPOS_PAYMENTS PM ON R.OBPOS_RECEIPTS_ID = PM.OBPOS_RECEIPTS_ID LEFT OUTER JOIN OBPOS_CUSTOMERS C ON C.OBPOS_CUSTOMERS_ID = T.OBPOS_CUSTOMERS_ID LEFT OUTER JOIN OBPOS_PEOPLE P ON T.OBPOS_PEOPLE_ID = OBPOS_PEOPLE.OBPOS_PEOPLE_ID " +
            "WHERE ?(QBF_FILTER) GROUP BY T.OBPOS_TICKETS_ID, T.TICKETID, T.TICKETTYPE, R.DATENEW, P.NAME, C.NAME ORDER BY R.DATENEW DESC, T.TICKETID", new String[] {"T.TICKETID", "T.TICKETTYPE", "PM.TOTAL", "R.DATENEW", "R.DATENEW", "P.NAME", "C.NAME"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.TIMESTAMP, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , new SerializerReadClass(FindTicketsInfo.class));
    }
   
    // Listados para combo
    public final ServiceHQL<ETax> getTaxService() {
        return new ServiceHQL<ETax>(App.getInstance().getUser().getHttpSession(), ETax.getClassEBean(),
                "where salesPurchaseType='S' and country.id=:parameter0 and (destinationCountry.id=:parameter0 or destinationCountry=null) and destinationRegion=null order by name");
    }
    
    public final ServiceHQL<ECategory> getCategoryService() {
        return new ServiceHQL<ECategory>(App.getInstance().getUser().getHttpSession(), ECategory.getClassEBean(), "order by name");
    }
    public final SentenceList getTaxCustCategoriesList() {
        return new StaticSentence(s
            , "SELECT C_BP_TAXCATEGORY_ID, NAME FROM C_BP_TAXCATEGORY ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new TaxCustCategoryInfo(dr.getString(1), dr.getString(2));
            }});
    }

    public final ServiceHQL<ETaxCategory> getTaxCategoryService() {
        return new ServiceHQL<ETaxCategory>(App.getInstance().getUser().getHttpSession(), ETaxCategory.getClassEBean(), "order by name");
    }

    public final SentenceList getAttributeSetList() {
        return new StaticSentence(s
            , "SELECT OBPOS_ATTRIBUTESET_ID, NAME FROM OBPOS_ATTRIBUTESET ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new AttributeSetInfo(dr.getString(1), dr.getString(2));
            }});
    }
    public final ServiceHQL<EWarehouse> getLocationsService() {
        return new ServiceHQL<EWarehouse>(App.getInstance().getUser().getHttpSession(), EWarehouse.getClassEBean(), "order by name");
    }

    public final ServiceHQL<EFloorView> getFloorsService() {
        return new ServiceHQL<EFloorView>(App.getInstance().getUser().getHttpSession(), EFloorView.getClassEBean(), "order by name");
    }

    public EBusinessPartner findBusinessPartner(String card) throws BasicException {
        return new ServiceHQL<EBusinessPartner>(App.getInstance().getUser().getHttpSession(), EBusinessPartner.getClassEBean(),
                "where searchKey = :parameter0").find(card);
    }
    
    public EBusinessPartner loadBusinessPartner(String id) throws BasicException {
        return new ServiceFind<EBusinessPartner>(App.getInstance().getUser().getHttpSession(), EBusinessPartner.getClassEBean()).find(id);
    }

    public EBusinessPartnerLocation findBusinessPartnerLocation(String businessPartner) throws BasicException {
        return new ServiceHQL<EBusinessPartnerLocation>(App.getInstance().getUser().getHttpSession(), EBusinessPartnerLocation.getClassEBean(),
                "from BusinessPartnerLocation where id = (select min(id) from BusinessPartnerLocation where businessPartner.id = :parameter0 and $readableCriteria)").
                find(businessPartner);
    }

    public final EOrder loadTicket(final int tickettype, final int ticketid) throws BasicException {
        EOrder ticket = (EOrder) new PreparedSentence(s
                , "SELECT T.OBPOS_TICKETS_ID, T.TICKETTYPE, T.TICKETID, R.DATENEW, R.OBPOS_CLOSEDCASH_ID, R.ATTRIBUTES, P.OBPOS_PEOPLE_ID, P.NAME, T.OBPOS_CUSTOMERS_ID "
                + "FROM OBPOS_RECEIPTS R JOIN OBPOS_TICKETS T ON R.OBPOS_RECEIPTS_ID = T.OBPOS_TICKETS_ID LEFT OUTER JOIN OBPOS_PEOPLE P ON T.OBPOS_PEOPLE_ID = P.OBPOS_PEOPLE_ID WHERE T.TICKETTYPE = ? AND T.TICKETID = ?"
                , SerializerWriteParams.INSTANCE
                , new SerializerReadClass(EOrder.class))
                .find(new DataParams() { public void writeValues() throws BasicException {
                    setInt(1, tickettype);
                    setInt(2, ticketid);
                }});
        if (ticket != null) {

            ticket.setLines(new PreparedSentence(s
                , "SELECT L.OBPOS_TICKETS_ID, L.LINE, L.OBPOS_PRODUCTS_ID, L.OBPOS_ATTRIBUTESETINSTANCE_ID, L.UNITS, L.PRICE, T.C_TAX_ID, T.NAME, T.C_TAXCATEGORY_ID, T.VALIDFROM, T.C_BP_TAXCATEGORY_ID, T.PARENT_TAX_ID, T.RATE, T.CASCADE, T.LINE, L.ATTRIBUTES " +
                  "FROM OBPOS_TICKETLINES L, C_TAX T WHERE L.C_TAX_ID = T.C_TAX_ID AND L.OBPOS_TICKETS_ID = ? ORDER BY L.LINE"
                , SerializerWriteString.INSTANCE
                , new SerializerReadClass(EOrderLine.class)).list(ticket.getId()));
            ticket.setPayments(new PreparedSentence(s
                , "SELECT PAYMENT, TOTAL, TRANSID FROM OBPOS_PAYMENTS WHERE OBPOS_RECEIPTS_ID = ?"
                , SerializerWriteString.INSTANCE
                , new SerializerReadClass(XOrderPaymentVoucher.class)).list(ticket.getId()));
        }
        return ticket;
    }

    public final ResultProcess saveTicket(final EOrder order) throws BasicException {

        new ServicePut<EOrder>(App.getInstance().getUser().getHttpSession(), EOrder.getClassEBean()).exec(order);

        for (EOrderLine l : order.getLines()) {
            new ServicePut<EOrderLine>(App.getInstance().getUser().getHttpSession(), EOrderLine.getClassEBean()).exec(l);
        }

        // Process...
        ResultProcess result = new ServiceProcedure(App.getInstance().getUser().getHttpSession(), "C_Order Post").exec(order);

        // Save Payments
        ServiceProcess<Object> savePayment = new ServiceProcess<Object>(App.getInstance().getUser().getHttpSession(), "org.openbravo.retail.posterminal.ProcessSavePayments", null);
        for(XOrderPayment payment: order.getPayments()) {
            savePayment.exec(payment);
        }

        return result;
    }

    public final void saveTicket(final EOrder ticket, final String location) throws BasicException {

        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {

                // Set Receipt Id
                if (ticket.getDocumentNo() == null) {
                    switch (ticket.getTicketType()) {
                        case EOrder.RECEIPT_NORMAL:
                            ticket.setDocumentNo(Integer.toString(getNextTicketIndex().intValue()));
                            break;
                        case EOrder.RECEIPT_REFUND:
                            ticket.setDocumentNo(Integer.toString(getNextTicketRefundIndex().intValue()));
                            break;
                        case EOrder.RECEIPT_PAYMENT:
                            ticket.setDocumentNo(Integer.toString(getNextTicketPaymentIndex().intValue()));
                            break;
                        default:
                            throw new BasicException();
                    }
                }

                // new receipt
                new PreparedSentence(s
                    , "INSERT INTO OBPOS_RECEIPTS (OBPOS_RECEIPTS_ID, OBPOS_CLOSEDCASH_ID, DATENEW, ATTRIBUTES) VALUES (?, ?, ?, ?)"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, ticket.getId());
                        setTimestamp(3, ticket.getOrderDate());
                        setBytes(4, ImageUtils.writeProperties(ticket.getProperties()));
                    }});

                // new ticket
                new PreparedSentence(s
                    , "INSERT INTO OBPOS_TICKETS (OBPOS_TICKETS_ID, TICKETTYPE, TICKETID, OBPOS_PEOPLE_ID, OBPOS_CUSTOMERS_ID) VALUES (?, ?, ?, ?, ?)"
                    , SerializerWriteParams.INSTANCE
                    ).exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, ticket.getId());
                        setInt(2, ticket.getTicketType());
                        setString(3, ticket.getDocumentNo()); // This will definitely fail the types are different. The complete method will be replaced.
                        setString(4, ticket.getUser().getId());
                        setString(5, ticket.getCustomer());
                    }});

                SentenceExec ticketlineinsert = new PreparedSentence(s
                    , "INSERT INTO OBPOS_TICKETLINES (OBPOS_TICKETS_ID, LINE, OBPOS_PRODUCTS_ID, OBPOS_ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, C_TAX_ID, ATTRIBUTES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                    , SerializerWriteBuilder.INSTANCE);

                for (EOrderLine l : ticket.getLines()) {
                    ticketlineinsert.exec(l);
                    if (l.getProduct() != null)  {
                        // update the stock
                        getStockDiaryInsert().exec(new Object[] {
                            StringUtils.newUUID(),
                            ticket.getOrderDate(),
                            l.getMultiply() < 0.0
                                ? MovementReason.IN_REFUND.getId()
                                : MovementReason.OUT_SALE.getId(),
                            location,
                            l.getProduct(),
                            l.getProductAttSetInstId(),
                            new Double(-l.getMultiply()),
                            new Double(l.getPrice())
                        });
                    }
                }

                SentenceExec paymentinsert = new PreparedSentence(s
                    , "INSERT INTO OBPOS_PAYMENTS (OBPOS_PAYMENTS_ID, OBPOS_RECEIPTS_ID, PAYMENT, TOTAL, TRANSID, RETURNMSG) VALUES (?, ?, ?, ?, ?, ?)"
                    , SerializerWriteParams.INSTANCE);
                for (final XOrderPayment p : ticket.getPayments()) {
                    paymentinsert.exec(new DataParams() { public void writeValues() throws BasicException {
                        setString(1, StringUtils.newUUID());
                        setString(2, ticket.getId());
//                        setString(3, p.getName());
                        setDouble(4, p.getTotal());
//                        setString(5, ticket.getTransactionID());
                        setBytes(6, (byte[]) Formats.BYTEA.parseValue(ticket.getReturnMessage()));
                    }});

//                    if ("debt".equals(p.getName()) || "debtpaid".equals(p.getName())) {
//
//                        // PENDING: Update BP credit used
//                        // udate customer fields...
//                        ticket.getCustomer().updateCurDebt(p.getTotal(), ticket.getDate());
//
//                        // save customer fields...
//                        getDebtUpdate().exec(new DataParams() { public void writeValues() throws BasicException {
//                            setDouble(1, ticket.getCustomer().getCurdebt());
//                            setTimestamp(2, ticket.getCustomer().getCurdate());
//                            setString(3, ticket.getCustomer().getId());
//                        }});
//                    }
                }

                SentenceExec taxlinesinsert = new PreparedSentence(s
                        , "INSERT INTO OBPOS_TAXLINES (OBPOS_TAXLINES_ID, OBPOS_RECEIPTS_ID, C_TAX_ID, BASE, AMOUNT)  VALUES (?, ?, ?, ?, ?)"
                        , SerializerWriteParams.INSTANCE);
                if (ticket.getTaxes() != null) {
                    for (final TicketTaxInfo tickettax: ticket.getTaxes()) {
                        taxlinesinsert.exec(new DataParams() { public void writeValues() throws BasicException {
                            setString(1, StringUtils.newUUID());
                            setString(2, ticket.getId());
                            setString(3, tickettax.getTaxInfo().getId());
                            setDouble(4, tickettax.getSubTotal());
                            setDouble(5, tickettax.getTax());
                        }});
                    }
                }

                return null;
            }
        };
        t.execute();
    }

    public final void deleteTicket(final EOrder ticket, final String location) throws BasicException {

        Transaction t = new Transaction(s) {
            public Object transact() throws BasicException {

                // update the inventory
                Date d = new Date();
                for (int i = 0; i < ticket.getLinesCount(); i++) {
                    if (ticket.getLine(i).getProduct() != null)  {
                        // Hay que actualizar el stock si el hay producto
                        getStockDiaryInsert().exec( new Object[] {
                            StringUtils.newUUID(),
                            d,
                            ticket.getLine(i).getMultiply() >= 0.0
                                ? MovementReason.IN_REFUND.getId()
                                : MovementReason.OUT_SALE.getId(),
                            location,
                            ticket.getLine(i).getProduct(),
                            ticket.getLine(i).getProductAttSetInstId(),
                            new Double(ticket.getLine(i).getMultiply()),
                            new Double(ticket.getLine(i).getPrice())
                        });
                    }
                }

//                // update customer debts
//                for (XOrderPayment p : ticket.getPayments()) {
//                    if ("debt".equals(p.getName()) || "debtpaid".equals(p.getName())) {
//                        // PENDING: Update BP credit used
//                        // udate customer fields...
//                        ticket.getCustomer().updateCurDebt(-p.getTotal(), ticket.getDate());
//
//                         // save customer fields...
//                        getDebtUpdate().exec(new DataParams() { public void writeValues() throws BasicException {
//                            setDouble(1, ticket.getCustomer().getCurdebt());
//                            setTimestamp(2, ticket.getCustomer().getCurdate());
//                            setString(3, ticket.getCustomer().getId());
//                        }});
//                    }
//                }

                // and delete the receipt
                new StaticSentence(s
                    , "DELETE FROM OBPOS_TAXLINES WHERE OBPOS_RECEIPTS_ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM OBPOS_PAYMENTS WHERE OBPOS_RECEIPTS_ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM OBPOS_TICKETLINES WHERE OBPOS_TICKETS_ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM OBPOS_TICKETS WHERE OBPOS_TICKETS_ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s
                    , "DELETE FROM OBPOS_RECEIPTS WHERE OBPOS_RECEIPTS_ID = ?"
                    , SerializerWriteString.INSTANCE).exec(ticket.getId());
                return null;
            }
        };
        t.execute();
    }

    public final Integer getNextTicketIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "OBPOS_TICKETSNUM").find();
    }

    public final Integer getNextTicketRefundIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "OBPOS_TICKETSNUM_REFUND").find();
    }

    public final Integer getNextTicketPaymentIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "OBPOS_TICKETSNUM_PAYMENT").find();
    }

    public final SentenceFind getProductImage() {
        return new PreparedSentence(s,
                "SELECT IMAGE FROM OBPOS_PRODUCTS WHERE ID = ?",
                SerializerWriteString.INSTANCE,
                new SerializerRead() {
                    @Override
                    public Object readValues(DataRead dr) throws BasicException {
                        return ImageUtils.readImage(dr.getBytes(1));
                    }
                });
    }

    public final SentenceList getProductCatQBF() {
        return new StaticSentence(s
            , new QBFBuilder(
                "SELECT P.OBPOS_PRODUCTS_ID, P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.OBPOS_CATEGORIES_ID, P.C_TAXCATEGORY_ID, P.OBPOS_ATTRIBUTESET_ID, " + s.DB.CHAR_NULL() + ", P.STOCKCOST, P.STOCKVOLUME, CASE WHEN C.OBPOS_PRODUCTS_ID IS NULL THEN 'N' ELSE 'Y' END, C.CATORDER, P.ATTRIBUTES " +
                "FROM OBPOS_PRODUCTS P LEFT OUTER JOIN OBPOS_PRODUCTS_CAT C ON P.OBPOS_PRODUCTS_ID = C.OBPOS_PRODUCTS_ID " +
                "WHERE ?(QBF_FILTER) " +
                "ORDER BY P.REFERENCE", new String[] {"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.OBPOS_CATEGORIES_ID", "P.CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.STRING})
            , productsRow.getSerializerRead());
    }

    public final SentenceExec getProductCatInsert() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                int i = new PreparedSentence(s
                    , "INSERT INTO OBPOS_PRODUCTS (OBPOS_PRODUCTS_ID, REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, OBPOS_CATEGORIES_ID, C_TAXCATEGORY_ID, OBPOS_ATTRIBUTESET_ID, IMAGE, STOCKCOST, STOCKVOLUME, ATTRIBUTES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16})).exec(params);
                if (i > 0 && ((Boolean)values[14]).booleanValue()) {
                    return new PreparedSentence(s
                        , "INSERT INTO OBPOS_PRODUCTS_CAT (OBPOS_PRODUCTS_ID, CATORDER) VALUES (?, ?)"
                        , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0, 15})).exec(params);
                } else {
                    return i;
                }
            }
        };
    }

    public final SentenceExec getProductCatUpdate() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                int i = new PreparedSentence(s
                    , "UPDATE OBPOS_PRODUCTS SET OBPOS_PRODUCTS_ID = ?, REFERENCE = ?, CODE = ?, NAME = ?, ISCOM = ?, ISSCALE = ?, PRICEBUY = ?, PRICESELL = ?, OBPOS_CATEGORIES_ID = ?, C_TAXCATEGORY_ID = ?, OBPOS_ATTRIBUTESET_ID = ?, IMAGE = ?, STOCKCOST = ?, STOCKVOLUME = ?, ATTRIBUTES = ? WHERE OBPOS_PRODUCTS_ID = ?"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 16, 0})).exec(params);
                if (i > 0) {
                    if (((Boolean)values[14]).booleanValue()) {
                        if (new PreparedSentence(s
                                , "UPDATE OBPOS_PRODUCTS_CAT SET CATORDER = ? WHERE OBPOS_PRODUCTS_ID = ?"
                                , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {15, 0})).exec(params) == 0) {
                            new PreparedSentence(s
                                , "INSERT INTO OBPOS_PRODUCTS_CAT (OBPOS_PRODUCTS_ID, CATORDER) VALUES (?, ?)"
                                , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0, 15})).exec(params);
                        }
                    } else {
                        new PreparedSentence(s
                            , "DELETE FROM OBPOS_PRODUCTS_CAT WHERE OBPOS_PRODUCTS_ID = ?"
                            , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0})).exec(params);
                    }
                }
                return i;
            }
        };
    }

    public final SentenceExec getProductCatDelete() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s
                    , "DELETE FROM OBPOS_PRODUCTS_CAT WHERE OBPOS_PRODUCTS_ID = ?"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0})).exec(params);
                return new PreparedSentence(s
                    , "DELETE FROM OBPOS_PRODUCTS WHERE OBPOS_PRODUCTS_ID = ?"
                    , new SerializerWriteBasicExt(productsRow.getDatas(), new int[] {0})).exec(params);
            }
        };
    }
    
// PENDING: Update BP credit used
//    public final SentenceExec getDebtUpdate() {
//
//        return new PreparedSentence(s
//                , "UPDATE OBPOS_CUSTOMERS SET CURDEBT = ?, CURDATE = ? WHERE OBPOS_CUSTOMERS_ID = ?"
//                , SerializerWriteParams.INSTANCE);
//    }

    public final SentenceExec getStockDiaryInsert() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                int updateresult = ((Object[]) params)[5] == null // si ATTRIBUTESETINSTANCE_ID is null
                    ? new PreparedSentence(s
                        , "UPDATE OBPOS_STOCKCURRENT SET UNITS = (UNITS + ?) WHERE OBPOS_LOCATIONS_ID = ? AND OBPOS_PRODUCTS_ID = ? AND OBPOS_ATTRIBUTESETINSTANCE_ID IS NULL"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4})).exec(params)
                    : new PreparedSentence(s
                        , "UPDATE OBPOS_STOCKCURRENT SET UNITS = (UNITS + ?) WHERE OBPOS_LOCATIONS_ID = ? AND OBPOS_PRODUCTS_ID = ? AND OBPOS_ATTRIBUTESETINSTANCE_ID = ?"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4, 5})).exec(params);

                if (updateresult == 0) {
                    new PreparedSentence(s
                        , "INSERT INTO OBPOS_STOCKCURRENT (OBPOS_LOCATIONS_ID, OBPOS_PRODUCTS_ID, OBPOS_ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, ?)"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(s
                    , "INSERT INTO OBPOS_STOCKDIARY (OBPOS_STOCKDIARY_ID, DATENEW, REASON, OBPOS_LOCATIONS_ID, OBPOS_PRODUCTS_ID, OBPOS_ATTRIBUTESETINSTANCE_ID, UNITS, PRICE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                    , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {0, 1, 2, 3, 4, 5, 6, 7})).exec(params);
            }
        };
    }

    public final SentenceExec getStockDiaryDelete() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                int updateresult = ((Object[]) params)[5] == null // if ATTRIBUTESETINSTANCE_ID is null
                        ? new PreparedSentence(s
                            , "UPDATE OBPOS_STOCKCURRENT SET UNITS = (UNITS - ?) WHERE OBPOS_LOCATIONS_ID = ? AND OBPOS_PRODUCTS_ID = ? AND OBPOS_ATTRIBUTESETINSTANCE_ID IS NULL"
                            , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4})).exec(params)
                        : new PreparedSentence(s
                            , "UPDATE OBPOS_STOCKCURRENT SET UNITS = (UNITS - ?) WHERE OBPOS_LOCATIONS_ID = ? AND OBPOS_PRODUCTS_ID = ? AND OBPOS_ATTRIBUTESETINSTANCE_ID = ?"
                            , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {6, 3, 4, 5})).exec(params);

                if (updateresult == 0) {
                    new PreparedSentence(s
                        , "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, -(?))"
                        , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(s
                    , "DELETE FROM OBPOS_STOCKDIARY WHERE OBPOS_STOCKDIARY_ID = ?"
                    , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {0})).exec(params);
            }
        };
    }

    public final SentenceExec getPaymentMovementInsert() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s
                    , "INSERT INTO OBPOS_RECEIPTS (OBPOS_RECEIPTS_ID, OBPOS_CLOSEDCASH_ID, DATENEW) VALUES (?, ?, ?)"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0, 1, 2})).exec(params);
                return new PreparedSentence(s
                    , "INSERT INTO OBPOS_PAYMENTS (OBPOS_PAYMENTS_ID, OBPOS_RECEIPTS_ID, PAYMENT, TOTAL) VALUES (?, ?, ?, ?)"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {3, 0, 4, 5})).exec(params);
            }
        };
    }

    public final SentenceExec getPaymentMovementDelete() {
        return new SentenceExecTransaction(s) {
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s
                    , "DELETE FROM OBPOS_PAYMENTS WHERE OBPOS_PAYMENTS_ID = ?"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {3})).exec(params);
                return new PreparedSentence(s
                    , "DELETE FROM OBPOS_RECEIPTS WHERE OBPOS_RECEIPTS_ID = ?"
                    , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0})).exec(params);
            }
        };
    }

    public final double findProductStock(String warehouse, String id, String attsetinstid) throws BasicException {

        PreparedSentence p = attsetinstid == null
                ? new PreparedSentence(s, "SELECT UNITS FROM OBPOS_STOCKCURRENT WHERE OBPOS_LOCATIONS_ID = ? AND OBPOS_PRODUCTS_ID = ? AND OBPOS_ATTRIBUTESETINSTANCE_ID IS NULL"
                    , new SerializerWriteBasic(Datas.STRING, Datas.STRING)
                    , SerializerReadDouble.INSTANCE)
                : new PreparedSentence(s, "SELECT UNITS FROM OBPOS_STOCKCURRENT WHERE OBPOS_LOCATIONS_ID = ? AND OBPOS_PRODUCTS_ID = ? AND OBPOS_ATTRIBUTESETINSTANCE_ID = ?"
                    , new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING)
                    , SerializerReadDouble.INSTANCE);

        Double d = (Double) p.find(warehouse, id, attsetinstid);
        return d == null ? 0.0 : d.doubleValue();
    }

    public final SentenceExec getCatalogCategoryAdd() {
        return new StaticSentence(s
                , "INSERT INTO OBPOS_PRODUCTS_CAT(OBPOS_PRODUCTS_ID, CATORDER) SELECT OBPOS_PRODUCTS_ID, " + s.DB.INTEGER_NULL() + " FROM OBPOS_PRODUCTS WHERE OBPOS_CATEGORIES_ID = ?"
                , SerializerWriteString.INSTANCE);
    }
    public final SentenceExec getCatalogCategoryDel() {
        return new StaticSentence(s
                , "DELETE FROM OBPOS_PRODUCTS_CAT WHERE OBPOS_PRODUCTS_ID = ANY (SELECT OBPOS_PRODUCTS_ID FROM OBPOS_PRODUCTS WHERE OBPOS_CATEGORIES_ID = ?)"
                , SerializerWriteString.INSTANCE);
    }

    public final TableDefinition getTableCategories() {
        return new TableDefinition(s,
            "OBPOS_CATEGORIES"
            , new String[] {"OBPOS_CATEGORIES_ID", "NAME", "PARENT_OBPOS_CATEGORIES_ID", "IMAGE"}
            , new String[] {"OBPOS_CATEGORIES_ID", AppLocal.getIntString("Label.Name"), "", AppLocal.getIntString("label.image")}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.IMAGE}
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING, Formats.NULL}
            , new int[] {0}
        );
    }
    public final TableDefinition getTableTaxes() {
        return new TableDefinition(s,
            "C_TAX"
            , new String[] {"C_TAX_ID", "NAME", "C_TAXCATEGORY_ID", "VALIDFROM", "C_BP_TAXCATEGORY_ID", "PARENT_TAX_ID", "RATE", "CASCADE", "LINE"}
            , new String[] {"C_TAX_ID", AppLocal.getIntString("Label.Name"), AppLocal.getIntString("label.taxcategory"), AppLocal.getIntString("Label.ValidFrom"), AppLocal.getIntString("label.custtaxcategory"), AppLocal.getIntString("label.taxparent"), AppLocal.getIntString("label.dutyrate"), AppLocal.getIntString("label.cascade"), AppLocal.getIntString("label.order")}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.TIMESTAMP, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.BOOLEAN, Datas.INT}
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING, Formats.TIMESTAMP, Formats.STRING, Formats.STRING, Formats.PERCENT, Formats.BOOLEAN, Formats.INT}
            , new int[] {0}
        );
    }

    public final TableDefinition getTableTaxCustCategories() {
        return new TableDefinition(s,
            "C_BP_TAXCATEGORY"
            , new String[] {"C_BP_TAXCATEGORY_ID", "NAME"}
            , new String[] {"C_BP_TAXCATEGORY_ID", AppLocal.getIntString("Label.Name")}
            , new Datas[] {Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.STRING, Formats.STRING}
            , new int[] {0}
        );
    }
    public final TableDefinition getTableTaxCategories() {
        return new TableDefinition(s,
            "C_TAXCATEGORY"
            , new String[] {"C_TAXCATEGORY_ID", "NAME"}
            , new String[] {"C_TAXCATEGORY_ID", AppLocal.getIntString("Label.Name")}
            , new Datas[] {Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.STRING, Formats.STRING}
            , new int[] {0}
        );
    }

    public final TableDefinition getTableLocations() {
        return new TableDefinition(s,
            "OBPOS_LOCATIONS"
            , new String[] {"OBPOS_LOCATIONS_ID", "NAME", "ADDRESS"}
            , new String[] {"OBPOS_LOCATIONS_ID", AppLocal.getIntString("label.locationname"), AppLocal.getIntString("label.locationaddress")}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING}
            , new int[] {0}
        );
    }

//    protected static class CustomerExtRead implements SerializerRead {
//        public Object readValues(DataRead dr) throws BasicException {
//            CustomerInfoExt c = new CustomerInfoExt(dr.getString(1));
//            c.setTaxid(dr.getString(2));
//            c.setSearchkey(dr.getString(3));
//            c.setName(dr.getString(4));
//            c.setCard(dr.getString(5));
//            c.setTaxCustomerID(dr.getString(6));
//            c.setNotes(dr.getString(7));
//            c.setMaxdebt(dr.getDouble(8));
//            c.setVisible(dr.getBoolean(9).booleanValue());
//            c.setCurdate(dr.getTimestamp(10));
//            c.setCurdebt(dr.getDouble(11));
//            c.setFirstname(dr.getString(12));
//            c.setLastname(dr.getString(13));
//            c.setEmail(dr.getString(14));
//            c.setPhone(dr.getString(15));
//            c.setPhone2(dr.getString(16));
//            c.setFax(dr.getString(17));
//            c.setAddress(dr.getString(18));
//            c.setAddress2(dr.getString(19));
//            c.setPostal(dr.getString(20));
//            c.setCity(dr.getString(21));
//            c.setRegion(dr.getString(22));
//            c.setCountry(dr.getString(23));
//
//            return c;
//        }
//    }
}
