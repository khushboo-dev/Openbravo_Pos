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

import com.openbravo.basic.BasicException;
import com.openbravo.data.json.ClassEBean;
import com.openbravo.data.json.EBean;
import com.openbravo.data.json.HttpSession;
import com.openbravo.data.json.JSONFormats;
import com.openbravo.data.json.JSONReadString;
import com.openbravo.data.json.ServiceHQL;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.pos.ticket.UserInfo;
import com.openbravo.pos.util.ThumbNailBuilder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.xml.parsers.SAXParser;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class EUser extends EBean {

    public static final String DEFAULT_PASSWORD = "openbravo";
    
    private static final ThumbNailBuilder iconbuilder = new ThumbNailBuilder(32, 32, "com/openbravo/images/yast_sysadmin.png");
    private static SAXParser m_sp = null;
    private static HashMap<String, String> m_oldclasses; // This is for backwards compatibility purposes
    private static final Logger logger = Logger.getLogger(EUser.class.getName());

    private String name = null;
    private String description = null;
    private String businessPartner = null;
    private String defaultClient = null;
    private String defaultOrganization = null;
    private String defaultRole = null;
    private String defaultWarehouse = null;
    private byte[] image = null;

    //private String password = null;
    //private String card = null;
    //private boolean visible = false;


    private Icon icon = null;
    private HttpSession httpsession = null;
    private Set<String> m_apermissions;

    static {
        initOldClasses();
    }

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("name", getName());
        json.put("description",  JSONFormats.STRING.writeJSON(getDescription()));
        json.put("businessPartner",  JSONFormats.STRING.writeJSON(getBusinessPartner()));
        json.put("defaultClient",  JSONFormats.STRING.writeJSON(getDefaultClient()));
        json.put("defaultOrganization",  JSONFormats.STRING.writeJSON(getDefaultOrganization()));
        json.put("defaultRole",  JSONFormats.STRING.writeJSON(getDefaultRole()));
        json.put("defaultWarehouse",  JSONFormats.STRING.writeJSON(getDefaultWarehouse()));
        json.put("bindaryData", JSONFormats.BYTEA.writeJSON(getImage()));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setName(json.getString("name"));
        setDescription(JSONFormats.STRING.readJSON(json.opt("description")));
        setBusinessPartner(JSONFormats.STRING.readJSON(json.opt("businessPartner")));
        setDefaultClient(JSONFormats.STRING.readJSON(json.opt("defaultClient")));
        setDefaultOrganization(JSONFormats.STRING.readJSON(json.opt("defaultOrganization")));
        setDefaultRole(JSONFormats.STRING.readJSON(json.opt("defaultRole")));
        setDefaultWarehouse(JSONFormats.STRING.readJSON(json.opt("defaultWarehouse")));
        setImage(JSONFormats.BYTEA.readJSON(json.get("bindaryData")));
    }

    public static ClassEBean<EUser> getClassEBean() {
        return new ClassEBean<EUser>() {
            @Override
            public EUser create() {
                return new EUser();
            }

            @Override
            public String getEntity() {
                return "OBPOS_User_POS";
            }
        };
    }

    public Icon getIcon() {
        return icon;
    }

    public UserInfo getUserInfo() {
        return new UserInfo(id, name);
    }

    public void authenticate(String url) throws BasicException {
        authenticate(url, DEFAULT_PASSWORD);
    }

    public void authenticate(String url, String password) throws BasicException {

        httpsession = null;
        m_apermissions = null;

        HttpSession session = new HttpSession(url, getName(), password);

        List<String> l = new ServiceHQL<String>(session, new JSONReadString("classname"),
                "select obposPos.classname as classname from OBPOS_POS_Access where $readableCriteria and role.id = :parameter0")
                .list(getDefaultRole());

        httpsession = session;

        fillPermissions(l);
    }

    public HttpSession getHttpSession() {
        return httpsession;
    }

    public boolean hasPermission(String classname) {
        return (m_apermissions == null) ? false : m_apermissions.contains(classname);
    }

    private void fillPermissions(List<String> l) {

        // inicializamos los permisos
        m_apermissions = new HashSet<String>();
        // Y lo que todos tienen permisos
        m_apermissions.add("com.openbravo.pos.forms.JPanelMenu");
        m_apermissions.add("Menu.Exit");

        for (String s : l) {
            m_apermissions.add(mapNewClass(s));
        }
    }

    private static String mapNewClass(String classname) {
        String newclass = m_oldclasses.get(classname);
        return newclass == null
                ? classname
                : newclass;
    }

    private static void initOldClasses() {
        m_oldclasses = new HashMap<String, String>();

        // update permissions from 0.0.24 to 2.20
        m_oldclasses.put("net.adrianromero.tpv.panelsales.JPanelTicketSales", "com.openbravo.pos.sales.JPanelTicketSales");
        m_oldclasses.put("net.adrianromero.tpv.panelsales.JPanelTicketEdits", "com.openbravo.pos.sales.JPanelTicketEdits");
        m_oldclasses.put("net.adrianromero.tpv.panels.JPanelPayments", "com.openbravo.pos.panels.JPanelPayments");
        m_oldclasses.put("net.adrianromero.tpv.panels.JPanelCloseMoney", "com.openbravo.pos.panels.JPanelCloseMoney");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportClosedPos", "/com/openbravo/reports/closedpos.bs");

//        m_oldclasses.put("payment.cash", "");
//        m_oldclasses.put("payment.cheque", "");
//        m_oldclasses.put("payment.paper", "");
//        m_oldclasses.put("payment.tichet", "");
//        m_oldclasses.put("payment.magcard", "");
//        m_oldclasses.put("payment.free", "");
//        m_oldclasses.put("refund.cash", "");
//        m_oldclasses.put("refund.cheque", "");
//        m_oldclasses.put("refund.paper", "");
//        m_oldclasses.put("refund.magcard", "");

        m_oldclasses.put("Menu.StockManagement", "com.openbravo.pos.forms.MenuStockManagement");
        m_oldclasses.put("net.adrianromero.tpv.inventory.ProductsPanel", "com.openbravo.pos.inventory.ProductsPanel");
        m_oldclasses.put("net.adrianromero.tpv.inventory.ProductsWarehousePanel", "com.openbravo.pos.inventory.ProductsWarehousePanel");
        m_oldclasses.put("net.adrianromero.tpv.inventory.CategoriesPanel", "com.openbravo.pos.inventory.CategoriesPanel");
        m_oldclasses.put("net.adrianromero.tpv.panels.JPanelTax", "com.openbravo.pos.inventory.TaxPanel");
        m_oldclasses.put("net.adrianromero.tpv.inventory.StockDiaryPanel", "com.openbravo.pos.inventory.StockDiaryPanel");
        m_oldclasses.put("net.adrianromero.tpv.inventory.StockManagement", "com.openbravo.pos.inventory.StockManagement");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportProducts", "/com/openbravo/reports/products.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportCatalog", "/com/openbravo/reports/productscatalog.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportInventory", "/com/openbravo/reports/inventory.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportInventory2", "/com/openbravo/reports/inventoryb.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportInventoryBroken", "/com/openbravo/reports/inventorybroken.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportInventoryDiff", "/com/openbravo/reports/inventorydiff.bs");

        m_oldclasses.put("Menu.SalesManagement", "com.openbravo.pos.forms.MenuSalesManagement");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportUserSales", "/com/openbravo/reports/usersales.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportClosedProducts", "/com/openbravo/reports/closedproducts.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JReportTaxes", "/com/openbravo/reports/taxes.bs");
        m_oldclasses.put("net.adrianromero.tpv.reports.JChartSales", "/com/openbravo/reports/chartsales.bs");

        m_oldclasses.put("Menu.Maintenance", "com.openbravo.pos.forms.MenuMaintenance");
        m_oldclasses.put("net.adrianromero.tpv.admin.PeoplePanel", "com.openbravo.pos.admin.PeoplePanel");
        m_oldclasses.put("net.adrianromero.tpv.admin.RolesPanel", "com.openbravo.pos.admin.RolesPanel");
        m_oldclasses.put("net.adrianromero.tpv.admin.ResourcesPanel", "com.openbravo.pos.admin.ResourcesPanel");
        m_oldclasses.put("net.adrianromero.tpv.inventory.LocationsPanel", "com.openbravo.pos.inventory.LocationsPanel");
        m_oldclasses.put("net.adrianromero.tpv.mant.JPanelFloors", "com.openbravo.pos.mant.JPanelFloors");
        m_oldclasses.put("net.adrianromero.tpv.mant.JPanelPlaces", "com.openbravo.pos.mant.JPanelPlaces");
        m_oldclasses.put("com.openbravo.possync.ProductsSync", "com.openbravo.possync.ProductsSyncCreate");
        m_oldclasses.put("com.openbravo.possync.OrdersSync", "com.openbravo.possync.OrdersSyncCreate");

        m_oldclasses.put("Menu.ChangePassword", "Menu.ChangePassword");
        m_oldclasses.put("net.adrianromero.tpv.panels.JPanelPrinter", "com.openbravo.pos.panels.JPanelPrinter");
        m_oldclasses.put("net.adrianromero.tpv.config.JPanelConfiguration", "com.openbravo.pos.config.JPanelConfiguration");

//        m_oldclasses.put("button.print", "");
//        m_oldclasses.put("button.opendrawer", "");

        // update permissions from 2.00 to 2.20
        m_oldclasses.put("com.openbravo.pos.reports.JReportCustomers", "/com/openbravo/reports/customers.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportCustomersB", "/com/openbravo/reports/customersb.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportClosedPos", "/com/openbravo/reports/closedpos.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportClosedProducts", "/com/openbravo/reports/closedproducts.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JChartSales", "/com/openbravo/reports/chartsales.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventory", "/com/openbravo/reports/inventory.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventory2", "/com/openbravo/reports/inventoryb.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventoryBroken", "/com/openbravo/reports/inventorybroken.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportInventoryDiff", "/com/openbravo/reports/inventorydiff.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportPeople", "/com/openbravo/reports/people.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportTaxes", "/com/openbravo/reports/taxes.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportUserSales", "/com/openbravo/reports/usersales.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportProducts", "/com/openbravo/reports/products.bs");
        m_oldclasses.put("com.openbravo.pos.reports.JReportCatalog", "/com/openbravo/reports/productscatalog.bs");

        // update permissions from 2.10 to 2.20
        m_oldclasses.put("com.openbravo.pos.panels.JPanelTax", "com.openbravo.pos.inventory.TaxPanel");
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
     * @return the defaultClient
     */
    public String getDefaultClient() {
        return defaultClient;
    }

    /**
     * @param defaultClient the defaultClient to set
     */
    public void setDefaultClient(String defaultClient) {
        this.defaultClient = defaultClient;
    }

    /**
     * @return the defaultOrganization
     */
    public String getDefaultOrganization() {
        return defaultOrganization;
    }

    /**
     * @param defaultOrganization the defaultOrganization to set
     */
    public void setDefaultOrganization(String defaultOrganization) {
        this.defaultOrganization = defaultOrganization;
    }

    /**
     * @return the defaultRole
     */
    public String getDefaultRole() {
        return defaultRole;
    }

    /**
     * @param defaultRole the defaultRole to set
     */
    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }

    /**
     * @return the defaultWarehouse
     */
    public String getDefaultWarehouse() {
        return defaultWarehouse;
    }

    /**
     * @param defaultWarehouse the defaultWarehouse to set
     */
    public void setDefaultWarehouse(String defaultWarehouse) {
        this.defaultWarehouse = defaultWarehouse;
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
     * @return the image
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(byte[] image) {
        this.image = image;
        this.icon = new ImageIcon(iconbuilder.getThumbNail(ImageUtils.readImage(image)));
    }
}
