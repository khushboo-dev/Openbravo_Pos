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

package com.openbravo.pos.inventory;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.Datas;
import com.openbravo.data.model.Column;
import com.openbravo.data.model.Field;
import com.openbravo.data.model.PrimaryKey;
import com.openbravo.data.model.Row;
import com.openbravo.data.model.Table;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.entities.EProduct;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.AuxiliarFilter;
import com.openbravo.pos.panels.JPanelTable2;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author jaroslawwozniak
 * @author adrianromero
 */
public class AuxiliarPanel extends JPanelTable2 {

    private AuxiliarEditor editor;
    private AuxiliarFilter filter;

    protected void init() {  
        
        filter = new AuxiliarFilter();
        filter.init(app);
        filter.addActionListener(new ReloadActionListener());
        
        row = new Row(
                new Field("OBPOS_PRODUCTS_COM_ID", Datas.STRING, Formats.STRING),
                new Field("OBPOS_PRODUCTS_ID", Datas.STRING, Formats.STRING),
                new Field("RELATED_OBPOS_PRODUCTS_ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true),
                new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true)
        );        
        Table table = new Table(
                "OBPOS_PRODUCTS_COM",
                new PrimaryKey("OBPOS_PRODUCTS_COM_ID"),
                new Column("OBPOS_PRODUCTS_ID"),
                new Column("RELATED_OBPOS_PRODUCTS_ID"));
         
        lpr = row.getListProvider(app.getSession(), 
                "SELECT COM.OBPOS_PRODUCTS_COM_ID, COM.OBPOS_PRODUCTS_ID, COM.RELATED_OBPOS_PRODUCTS_ID, P.REFERENCE, P.CODE, P.NAME " +
                "FROM OBPOS_PRODUCTS COM, OBPOS_PRODUCTS P " +
                "WHERE COM.RELATED_OBPOS_PRODUCTS_ID = P.OBPOS_PRODUCTS_ID AND COM.OBPOS_PRODUCTS_ID = ?", filter);
        spr = row.getSaveProvider(app.getSession(), table);              
        
        editor = new AuxiliarEditor(app, dirty);
    }

    @Override
    public void activate() throws BasicException {
        filter.activate();
        
        super.activate(); // PENDING: bd.actionLoad in activate must not be executed.
        // startNavigation();
        reload(filter);
    }

    @Override
    public Component getFilter(){
        return filter.getComponent();
    }
    
    public EditorRecord createEditor() {
        return editor;
    }  
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Auxiliar");
    } 
    
    private void reload(AuxiliarFilter filter) throws BasicException {
        EProduct prod = filter.getProduct();
        editor.setInsertProduct(prod); // must be set before load
        bd.setEditable(prod != null);
        bd.actionLoad();
    }
            
    private class ReloadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reload((AuxiliarFilter) e.getSource());
            } catch (BasicException w) {
            }
        }
    }
}
