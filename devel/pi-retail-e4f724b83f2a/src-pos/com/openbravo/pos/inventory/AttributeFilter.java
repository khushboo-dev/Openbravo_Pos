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
import com.openbravo.data.gui.ComboBoxValModel;
import com.openbravo.data.loader.DataRead;
import com.openbravo.data.loader.SentenceList;
import com.openbravo.data.loader.SerializerRead;
import com.openbravo.data.loader.SerializerWrite;
import com.openbravo.data.loader.SerializerWriteString;
import com.openbravo.data.loader.StaticSentence;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.reports.ReportEditorCreator;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;

/**
 *
 * @author adrianromero
 */
public class AttributeFilter extends javax.swing.JPanel implements ReportEditorCreator {

    private SentenceList attsent;
    private ComboBoxValModel attmodel;

    /** Creates new form AttributeUseFilter */
    public AttributeFilter() {
        initComponents();
    }

    public void init(AppView app) {

        attsent = new StaticSentence(app.getSession()
            , "SELECT OBPOS_ATTRIBUTE_ID, NAME FROM OBPOS_ATTRIBUTE ORDER BY NAME"
            , null
            , new SerializerRead() { public Object readValues(DataRead dr) throws BasicException {
                return new AttributeInfo(dr.getString(1), dr.getString(2));
            }});
        attmodel = new ComboBoxValModel();
    }

    public void activate() throws BasicException {
        List a = attsent.list();
        attmodel = new ComboBoxValModel(a);
        attmodel.setSelectedFirst();
        jAttr.setModel(attmodel);
    }

    public SerializerWrite getSerializerWrite() {
        return SerializerWriteString.INSTANCE;
    }

    public Component getComponent() {
        return this;
    }

    public void addActionListener(ActionListener l) {
        jAttr.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        jAttr.removeActionListener(l);
    }

    public Object createValue() throws BasicException {
        AttributeInfo att = (AttributeInfo) attmodel.getSelectedItem();

        return att == null ? null : att.getId();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jAttr = new javax.swing.JComboBox();

        jLabel8.setText(AppLocal.getIntString("label.attribute")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 366, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jAttr, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jAttr, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jAttr;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables

}