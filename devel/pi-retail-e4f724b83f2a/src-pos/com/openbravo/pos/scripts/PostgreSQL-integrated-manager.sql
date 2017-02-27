-- MANAGER
INSERT INTO OBPOS_POS_ACCESS (OBPOS_POS_ACCESS_ID, AD_CLIENT_ID, AD_ORG_ID, CREATEDBY, UPDATEDBY, AD_ROLE_ID, OBPOS_POS_ID)
SELECT GET_UUID(), 'FF8080812AFBCB14012AFBD3E373001F', '0', '0', '0', '58C14869508249C19C7B3D3ECBCDA509', OBPOS_POS_ID FROM OBPOS_POS WHERE CLASSNAME IN (
'com.openbravo.pos.sales.JPanelTicketSales',
'com.openbravo.pos.sales.JPanelTicketEdits',
'com.openbravo.pos.customers.CustomersPayment',
'com.openbravo.pos.panels.JPanelPayments',
'com.openbravo.pos.panels.JPanelCloseMoney',
'/com/openbravo/reports/closedpos.bs',
'sales.EditLines',
'sales.RefundTicket',
'sales.PrintTicket',
'sales.Total',
'payment.cash',
'payment.cheque',
'payment.paper',
'payment.magcard',
'payment.free',
'payment.debt',
'refund.cash',
'refund.cheque',
'refund.paper',
'refund.magcard',
'com.openbravo.pos.forms.MenuCustomers',
'com.openbravo.pos.customers.CustomersPanel',
'/com/openbravo/reports/customers.bs',
'/com/openbravo/reports/customersb.bs',
'/com/openbravo/reports/customersdiary.bs',
'com.openbravo.pos.inventory.TaxCustCategoriesPanel',
'com.openbravo.pos.forms.MenuStockManagement',
'com.openbravo.pos.inventory.ProductsPanel',
'com.openbravo.pos.inventory.ProductsWarehousePanel',
'com.openbravo.pos.inventory.CategoriesPanel',
'com.openbravo.pos.inventory.AttributesPanel',
'com.openbravo.pos.inventory.AttributeValuesPanel',
'com.openbravo.pos.inventory.AttributeSetsPanel',
'com.openbravo.pos.inventory.AttributeUsePanel',
'com.openbravo.pos.inventory.TaxPanel',
'com.openbravo.pos.inventory.TaxCategoriesPanel',
'com.openbravo.pos.inventory.StockDiaryPanel',
'com.openbravo.pos.inventory.StockManagement',
'com.openbravo.pos.inventory.AuxiliarPanel',
'/com/openbravo/reports/products.bs',
'/com/openbravo/reports/productlabels.bs'
'/com/openbravo/reports/productscatalog.bs',
'/com/openbravo/reports/inventory.bs',
'/com/openbravo/reports/inventoryb.bs',
'/com/openbravo/reports/inventorybroken.bs',
'/com/openbravo/reports/inventorylistdetail.bs',
'/com/openbravo/reports/inventorydiff.bs',
'/com/openbravo/reports/inventorydiffdetail.bs',
'com.openbravo.pos.forms.MenuSalesManagement',
'/com/openbravo/reports/usersales.bs',
'/com/openbravo/reports/closedproducts.bs',
'/com/openbravo/reports/taxes.bs',
'/com/openbravo/reports/chartsales.bs',
'/com/openbravo/reports/productsales.bs',
'Menu.ChangePassword',
'button.print',
'button.opendrawer'
);

INSERT INTO AD_WINDOW_ACCESS(AD_WINDOW_ACCESS_ID, AD_CLIENT_ID, AD_ORG_ID, CREATEDBY, UPDATEDBY,
AD_ROLE_ID, AD_WINDOW_ID, ISREADWRITE)
SELECT GET_UUID(), AD_CLIENT_ID, '0', '0', '0',
'58C14869508249C19C7B3D3ECBCDA509', AD_WINDOW_ID, ISREADWRITE FROM AD_WINDOW_ACCESS WHERE AD_ROLE_ID = 'FF8081812250326E012250353BDE0006';

INSERT INTO AD_PROCESS_ACCESS(AD_PROCESS_ACCESS_ID, AD_CLIENT_ID, AD_ORG_ID, CREATEDBY, UPDATEDBY,
AD_ROLE_ID, AD_PROCESS_ID, ISREADWRITE)
SELECT GET_UUID(), AD_CLIENT_ID, '0', '0', '0',
'58C14869508249C19C7B3D3ECBCDA509', AD_PROCESS_ID, ISREADWRITE FROM AD_PROCESS_ACCESS WHERE AD_ROLE_ID = 'FF8081812250326E012250353BDE0006';

INSERT INTO AD_FORM_ACCESS(AD_FORM_ACCESS_ID, AD_CLIENT_ID, AD_ORG_ID, CREATEDBY, UPDATEDBY,
AD_ROLE_ID, AD_FORM_ID, ISREADWRITE)
SELECT GET_UUID(), AD_CLIENT_ID, '0', '0', '0',
'58C14869508249C19C7B3D3ECBCDA509', AD_FORM_ID, ISREADWRITE FROM AD_FORM_ACCESS WHERE AD_ROLE_ID = 'FF8081812250326E012250353BDE0006';


INSERT INTO AD_ROLE_ORGACCESS(AD_ROLE_ORGACCESS_ID, AD_CLIENT_ID, AD_ORG_ID, CREATEDBY, UPDATEDBY,
AD_ROLE_ID, IS_ORG_ADMIN)
SELECT GET_UUID(), AD_CLIENT_ID, AD_ORG_ID, '0', '0',
'58C14869508249C19C7B3D3ECBCDA509', IS_ORG_ADMIN FROM AD_ROLE_ORGACCESS WHERE AD_ROLE_ID = 'FF8081812250326E012250353BDE0006';