//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2008-2009 Openbravo, S.L.
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
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.panels.JPanelTable2;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author adrianromero
 */
public class AttributeValuesPanel extends JPanelTable2 {

    private AttributeValuesEditor editor;
    private AttributeFilter filter;

    protected void init() {

        filter = new AttributeFilter();
        filter.init(app);
        filter.addActionListener(new ReloadActionListener());

        row = new Row(
                new Field("OBPOS_ATTRIBUTEVALUE_ID", Datas.STRING, Formats.STRING),
                new Field("OBPOS_ATTRIBUTE_ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.value"), Datas.STRING, Formats.STRING, true, true, true)
        );

        Table table = new Table(
                "OBPOS_ATTRIBUTEVALUE",
                new PrimaryKey("OBPOS_ATTRIBUTEVALUE_ID"),
                new Column("OBPOS_ATTRIBUTE_ID"),
                new Column("VALUE"));

        lpr = row.getListProvider(app.getSession(),
                "SELECT OBPOS_ATTRIBUTEVALUE_ID, OBPOS_ATTRIBUTE_ID, VALUE FROM OBPOS_ATTRIBUTEVALUE WHERE OBPOS_ATTRIBUTE_ID = ? ", filter);
        spr = row.getSaveProvider(app.getSession(), table);

        editor = new AttributeValuesEditor(dirty);
    }

    @Override
    public void activate() throws BasicException {
        filter.activate();

        super.activate(); // PENDING: bd.actionLoad in activate must not be executed.
        // startNavigation();
        reload();
    }

    @Override
    public Component getFilter(){
        return filter.getComponent();
    }

    public EditorRecord createEditor() {
        return editor;
    }

    private void reload() throws BasicException {

        String attid = (String) filter.createValue();
        editor.setInsertId(attid); // must be set before load
        bd.setEditable(attid != null);
        bd.actionLoad();
    }

    public String getTitle() {
        return AppLocal.getIntString("Menu.AttributeValues");
    }

    private class ReloadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                reload();
            } catch (BasicException w) {
            }
        }
    }
}