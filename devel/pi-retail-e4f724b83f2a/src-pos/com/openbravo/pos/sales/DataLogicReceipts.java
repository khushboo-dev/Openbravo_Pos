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

package com.openbravo.pos.sales;

import java.util.List;
import com.openbravo.basic.BasicException;
import com.openbravo.data.json.ServiceDelete;
import com.openbravo.data.json.ServiceFind;
import com.openbravo.data.json.ServicePut;
import com.openbravo.data.json.ServiceHQL;
import com.openbravo.data.loader.Session;
import com.openbravo.pos.entities.ESharedTicket;
import com.openbravo.pos.entities.ESharedTicketView;
import com.openbravo.pos.forms.BeanFactoryDataSingle;
import com.openbravo.pos.entities.EOrder;
import com.openbravo.pos.forms.App;

/**
 *
 * @author adrianromero
 */
public class DataLogicReceipts extends BeanFactoryDataSingle {
    
    private Session s;
    
    /** Creates a new instance of DataLogicReceipts */
    public DataLogicReceipts() {
    }
    
    public void init(Session s){
        this.s = s;
    }
     
    public final EOrder getSharedTicket(String id) throws BasicException {
        
        if (id == null) {
            return null; 
        } else {
            ESharedTicket sharedticket = new ServiceFind<ESharedTicket>(App.getInstance().getUser().getHttpSession(), ESharedTicket.getClassEBean()).find(id);
            return sharedticket.getTicketInfo();
        }
    } 
    
    public final List<ESharedTicketView> getSharedTicketList() throws BasicException {

        return new ServiceHQL<ESharedTicketView>(App.getInstance().getUser().getHttpSession(), ESharedTicketView.getClassEBean(), "order by name").list();
    }

    public final ESharedTicket saveSharedTicket(String id, String tableid, EOrder ticket) throws BasicException {

        ESharedTicket esharedticket = new ESharedTicket();
        esharedticket.setId(id);
        esharedticket.setPlace(tableid);
        esharedticket.setTicketInfo(ticket);

        new ServicePut<ESharedTicket>(App.getInstance().getUser().getHttpSession(), ESharedTicket.getClassEBean()).exec(esharedticket);

        return esharedticket;
    }

    public final void deleteSharedTicket(String id) throws BasicException {

        new ServiceDelete(App.getInstance().getUser().getHttpSession(), ESharedTicket.getClassEBean()).exec(id);
    }
}
