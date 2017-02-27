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

import java.awt.Component;
import com.openbravo.pos.util.StringUtils;
import com.openbravo.basic.BasicException;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.format.Formats;
import com.openbravo.pos.forms.AppLocal;

/**
 *
 * @author adrianromero
 */
public class LocationsView extends javax.swing.JPanel implements EditorRecord {
    
    // private DirtyManager m_Dirty = new DirtyManager();    
    private String m_sID;
    
    /** Creates new form LocationsEditor */
    public LocationsView(DirtyManager dirty) {
        initComponents();
        
        m_jName.getDocument().addDocumentListener(dirty);
        m_jAddress.getDocument().addDocumentListener(dirty);
        
        writeValueEOF();    
    }
    public void writeValueEOF() {
        
        m_sID = null;
        m_jName.setText(null);
        m_jAddress.setText(null);

        m_jName.setEnabled(false);
        m_jAddress.setEnabled(false);
    }    
    public void writeValueInsert() {
        
        m_sID = StringUtils.newUUID(); 
        m_jName.setText(null);
        m_jAddress.setText(null);

        m_jName.setEnabled(true);
        m_jAddress.setEnabled(true);
    }    
    public void writeValueDelete(Object value) {
        
        Object[] location = (Object[]) value;
        m_sID = Formats.STRING.formatValue(location[0]);
        m_jName.setText(Formats.STRING.formatValue(location[1]));
        m_jAddress.setText(Formats.STRING.formatValue(location[2]));

        m_jName.setEnabled(false);
        m_jAddress.setEnabled(false);
    }    
    public void writeValueEdit(Object value) {
        
        Object[] location = (Object[]) value;
        m_sID = Formats.STRING.formatValue(location[0]);
        m_jName.setText(Formats.STRING.formatValue(location[1]));
        m_jAddress.setText(Formats.STRING.formatValue(location[2]));

        m_jName.setEnabled(true);
        m_jAddress.setEnabled(true);
    }    
    public Object readValueEdit() throws BasicException {
        Object[] location = new Object[3];
        location[0] = m_sID;
        location[1] = m_jName.getText();
        location[2] = m_jAddress.getText();
        return location;
    }    
    
    public Component getEditorComponent() {
        return this;
    }
    
    public void refresh() {
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel2 = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jAddress = new javax.swing.JTextField();

        setLayout(null);

        jLabel2.setText(AppLocal.getIntString("label.locationname"));
        add(jLabel2);
        jLabel2.setBounds(20, 20, 80, 15);

        add(m_jName);
        m_jName.setBounds(100, 20, 260, 19);

        jLabel3.setText(AppLocal.getIntString("label.locationaddress"));
        add(jLabel3);
        jLabel3.setBounds(20, 50, 80, 15);

        add(m_jAddress);
        m_jAddress.setBounds(100, 50, 260, 19);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField m_jAddress;
    private javax.swing.JTextField m_jName;
    // End of variables declaration//GEN-END:variables
    
}
