/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.panels;

import com.openbravo.basic.BasicException;
import com.openbravo.data.gui.JCounter;
import com.openbravo.data.gui.JLabelDirty;
import com.openbravo.data.gui.JListNavigator;
import com.openbravo.data.gui.JNavigator;
import com.openbravo.data.gui.JSaver;
import com.openbravo.data.gui.MessageInf;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.data.user.BrowsableEditableData;
import com.openbravo.data.user.DirtyManager;
import com.openbravo.data.user.EditorRecord;
import com.openbravo.data.user.ListProvider;
import com.openbravo.data.user.SaveProvider;
import com.openbravo.pos.forms.AppLocal;
import com.openbravo.pos.forms.AppView;
import com.openbravo.pos.forms.BeanFactoryApp;
import com.openbravo.pos.forms.BeanFactoryException;
import com.openbravo.pos.forms.JPanelView;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author adrian
 */
public abstract class MaintenancePanel<T> extends JPanel implements JPanelView, BeanFactoryApp, EditorRecord<T> {

    protected BrowsableEditableData bd;
    protected DirtyManager dirty;

    protected MaintenanceContainer maintenancecontainer;

    public MaintenancePanel() {
        dirty = new DirtyManager();
        bd = null;
        maintenancecontainer = new MaintenanceContainer();
    }

    public final DirtyManager getDirtyManager() {
        return dirty;
    }

    public final BrowsableEditableData getBrowsableEditableData() {
        return bd;
    }

    public void init(AppView app) throws BeanFactoryException {
    }

    public Object getBean() {
        return this;
    }

    public JComponent getViewComponent() {
        return maintenancecontainer;
    }

    public Component getEditorComponent() {
        return this;
    }

    public void activate() throws BasicException {

        if (bd == null) {
            // init browsable editable data
            bd = new BrowsableEditableData(createListProvider(), createSaveProvider(), this, dirty);
            maintenancecontainer.init(this);
        }
        bd.actionLoad();
    }

    public boolean deactivate() {

        try {
            return bd.actionClosingForm(this);
        } catch (BasicException eD) {
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.CannotMove"), eD);
            msg.show(this);
            return false;
        }
    }

    public abstract ListProvider createListProvider();
    public abstract SaveProvider createSaveProvider();

    public Component getToolbarExtras() {
        return null;
    }

    public Component getFilter() {
        return null;
    }

    public Vectorer getVectorer() {
        return null;
    }

    public ComparatorCreator getComparatorCreator() {
        return null;
    }

    public ListCellRenderer getListCellRenderer() {
        return null;
    }
}
