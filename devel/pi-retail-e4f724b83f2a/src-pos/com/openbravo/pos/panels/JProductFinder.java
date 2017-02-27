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

package com.openbravo.pos.panels;

import com.openbravo.pos.ticket.ProductFilterSales;
import com.openbravo.pos.ticket.ProductRenderer;
import javax.swing.*;
import java.awt.*;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.basic.BasicException;
import com.openbravo.data.json.HQLParam;
import com.openbravo.data.json.JSONFormats;
import com.openbravo.data.json.QBFParam;
import com.openbravo.data.json.ServiceQBF;
import com.openbravo.data.user.EditorCreator;
import com.openbravo.pos.entities.EProduct;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.DataLogicSales;

/**
 *
 * @author adrianromero
 */
public class JProductFinder extends javax.swing.JDialog {

    private AppView app;
    private EProduct m_ReturnProduct;
    private ServiceQBF<EProduct> lpr;
    private EditorCreator filtervalues;
    
    public final static int PRODUCT_ALL = 0;
    public final static int PRODUCT_NORMAL = 1;
    public final static int PRODUCT_AUXILIAR = 2;
    
    /** Creates new form JProductFinder */
    private JProductFinder(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }
    /** Creates new form JProductFinder */
    private JProductFinder(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
    }
    
    private EProduct init(AppView app, DataLogicSales dlSales, int productsType) {
        
        initComponents();
        setLocationRelativeTo(getParent());

        this.app = app;
        
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));

        ProductFilterSales jproductfilter = new ProductFilterSales(dlSales, m_jKeys);
        jproductfilter.activate();
        m_jProductSelect.add(jproductfilter, BorderLayout.CENTER);
        
        this.filtervalues = jproductfilter;

        switch (productsType) {
        case PRODUCT_NORMAL:
            lpr = dlSales.getProductListNormal();
            break;
        case PRODUCT_AUXILIAR:
            lpr = dlSales.getProductListAuxiliar();
            break;
        default: // PRODUCT_ALL
            lpr = dlSales.getProductList();
            break;
        }
       
        jListProducts.setCellRenderer(new ProductRenderer());
        
        getRootPane().setDefaultButton(jcmdOK);   
   
        m_ReturnProduct = null;
        
        //show();
        setVisible(true);
        
        return m_ReturnProduct;
    }
    
    
    private static Window getWindow(Component parent) {
        if (parent == null) {
            return new JFrame();
        } else if (parent instanceof Frame || parent instanceof Dialog) {
            return (Window)parent;
        } else {
            return getWindow(parent.getParent());
        }
    }    
    
    public static EProduct showMessage(Component parent, AppView app, DataLogicSales dlSales) {
        return showMessage(parent, app, dlSales, PRODUCT_ALL);
    }

    public static EProduct showMessage(Component parent, AppView app, DataLogicSales dlSales, int productsType) {

        Window window = getWindow(parent);

        JProductFinder myMsg;
        if (window instanceof Frame) {
            myMsg = new JProductFinder((Frame) window, true);
        } else {
            myMsg = new JProductFinder((Dialog) window, true);
        }
        return myMsg.init(app, dlSales, productsType);
    }
    
    private static class MyListData extends javax.swing.AbstractListModel {
        
        private java.util.List m_data;
        
        public MyListData(java.util.List data) {
            m_data = data;
        }
        
        public Object getElementAt(int index) {
            return m_data.get(index);
        }
        
        public int getSize() {
            return m_data.size();
        } 
    }

    public Object[] getParameters() throws BasicException {
//        name, xx, netListPrice, pOSCategory, uPCEAN

        Object[] qbfvalues = (Object[]) filtervalues.createValue();

        Object[] params = new Object[5];
        params[0] = new QBFParam("name", qbfvalues, 0, JSONFormats.STRING);
        params[1] = new QBFParam("netListPrice", qbfvalues, 1, JSONFormats.DOUBLE);
        params[2] = new QBFParam("pOSCategory.id", qbfvalues, 2, JSONFormats.STRING);
        params[3] = new QBFParam("uPCEAN", qbfvalues, 3, JSONFormats.STRING);
        params[4] = new HQLParam("priceListVersion", app.getPriceListVersion(), JSONFormats.STRING);

        return params;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        m_jKeys = new com.openbravo.editor.JEditorKeys();
        jPanel2 = new javax.swing.JPanel();
        m_jProductSelect = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListProducts = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jcmdOK = new javax.swing.JButton();
        jcmdCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(AppLocal.getIntString("form.productslist")); // NOI18N

        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel4.add(m_jKeys, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        m_jProductSelect.setLayout(new java.awt.BorderLayout());

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/launch.png"))); // NOI18N
        jButton3.setText(AppLocal.getIntString("button.executefilter")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        m_jProductSelect.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel2.add(m_jProductSelect, java.awt.BorderLayout.NORTH);

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jListProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListProducts.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListProductsValueChanged(evt);
            }
        });
        jListProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListProducts);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jcmdOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/button_ok.png"))); // NOI18N
        jcmdOK.setText(AppLocal.getIntString("Button.OK")); // NOI18N
        jcmdOK.setEnabled(false);
        jcmdOK.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdOKActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdOK);

        jcmdCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/openbravo/images/button_cancel.png"))); // NOI18N
        jcmdCancel.setText(AppLocal.getIntString("Button.Cancel")); // NOI18N
        jcmdCancel.setMargin(new java.awt.Insets(8, 16, 8, 16));
        jcmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmdCancelActionPerformed(evt);
            }
        });
        jPanel1.add(jcmdCancel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-665)/2, (screenSize.height-565)/2, 665, 565);
    }// </editor-fold>//GEN-END:initComponents

    private void jListProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListProductsMouseClicked

        if (evt.getClickCount() == 2) {
            m_ReturnProduct = (EProduct) jListProducts.getSelectedValue();
            dispose();
        }
        
    }//GEN-LAST:event_jListProductsMouseClicked

    private void jcmdOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdOKActionPerformed
        
        m_ReturnProduct = (EProduct) jListProducts.getSelectedValue();
        dispose();
        
    }//GEN-LAST:event_jcmdOKActionPerformed

    private void jcmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmdCancelActionPerformed
        
        dispose();
        
    }//GEN-LAST:event_jcmdCancelActionPerformed

    private void jListProductsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListProductsValueChanged

        jcmdOK.setEnabled(jListProducts.getSelectedValue() != null);
        
    }//GEN-LAST:event_jListProductsValueChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            jListProducts.setModel(new MyListData(lpr.list(getParameters())));
            if (jListProducts.getModel().getSize() > 0) {
                jListProducts.setSelectedIndex(0);
            }
        } catch (BasicException e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JList jListProducts;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jcmdCancel;
    private javax.swing.JButton jcmdOK;
    private com.openbravo.editor.JEditorKeys m_jKeys;
    private javax.swing.JPanel m_jProductSelect;
    // End of variables declaration//GEN-END:variables
    
}
