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

import com.openbravo.data.json.HttpSession;
import com.openbravo.data.loader.Session;
import com.openbravo.pos.entities.EBusinessPartner;
import com.openbravo.pos.entities.EBusinessPartnerLocation;
import com.openbravo.pos.entities.ELocation;
import com.openbravo.pos.entities.EPriceList;
import com.openbravo.pos.entities.EProduct;
import com.openbravo.pos.entities.ETerminal;
import com.openbravo.pos.entities.ETerminalPayment;
import com.openbravo.pos.printer.*;
import com.openbravo.pos.sales.TaxesLogic;
import com.openbravo.pos.scale.DeviceScale;
import com.openbravo.pos.scanpal2.DeviceScanner;
import java.util.List;

/**
 *
 * @author adrianromero
 */
public interface AppView {
    
    public DeviceScale getDeviceScale();
    public DeviceTicket getDeviceTicket();
    public DeviceScanner getDeviceScanner();
      
    public Session getSession();
    public HttpSession getHttpSession();
    public AppProperties getProperties();
    public Object getBean(String beanfactory) throws BeanFactoryException;
    
    public ETerminal getTerminal();
    public List<ETerminalPayment> getTerminalPayments();
    public ETerminalPayment getTerminalPayment(String searchKey);
    public EBusinessPartner getBusinessPartner();
    public EBusinessPartnerLocation getBusinessPartnerLocation();
    public ELocation getOrganizationLocation();
    public EPriceList getPriceList();
    public EProduct getProduct();
    public String getPriceListVersion();
    public TaxesLogic getTaxesLogic();
    
    public void waitCursorBegin();
    public void waitCursorEnd();
    
    public AppUserView getUser();
}

