/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.data.json;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.ComparatorCreator;
import com.openbravo.data.loader.Vectorer;
import com.openbravo.pos.forms.AppLocal;
import java.util.Comparator;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ListCellRenderer;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public abstract class ClassEBean<T extends EBean> {

    public abstract T create();
    public abstract String getEntity();

    public final JSONObject toJSON(T bean) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("_entityName", getEntity());
        json.put("id", bean.getId());
        json.put("client", bean.getClient());
        json.put("organization", bean.getOrg());
        json.put("active", bean.isActive());
        bean.writeJSON(json);
        return json;
    }

    public final T fromJSON(JSONObject json) throws JSONException {
        T obj = create();
        ClassEBean.copyFromJSON(obj, json);
        obj.readJSON(json);
        return obj;
    }

    public static void copyFromJSON(EBean obj, JSONObject json) throws JSONException {
        obj.setId(json.getString("id"));
        obj.setIdentifier(json.getString("_identifier"));
        obj.setClient(json.getString("client"));
        obj.setOrg(json.getString("organization"));
        obj.setActive(json.getBoolean("active"));
        obj.setCreated(JSONFormats.TIMESTAMP.readJSON(json.get("creationDate")));
        obj.setCreatedBy(JSONFormats.STRING.readJSON(json.get("createdBy")));
        obj.setUpdated(JSONFormats.TIMESTAMP.readJSON(json.get("updated")));
        obj.setUpdatedBy(JSONFormats.STRING.readJSON(json.get("updatedBy")));
    }

    public T refreshFromJSON(T obj, JSONObject json) throws JSONException {
        return fromJSON(json);
    }

    public Vectorer<T> createVectorer() {
        return new Vectorer<T>() {
            public String[] getHeaders() throws BasicException {
                return new String[] {AppLocal.getIntString("label.identifier")};
            }

            public String[] getValues(T obj) throws BasicException {
                return new String[] { obj.getIdentifier() };
            }
        };
    }

    public ComparatorCreator<T> createComparatorCreator() {
        return new ComparatorCreator<T>() {

            public String[] getHeaders() {
                return new String[] {AppLocal.getIntString("label.identifier"), AppLocal.getIntString("label.created"), AppLocal.getIntString("label.updated")};
            }

            public Comparator<T> createComparator(int[] indexes) {
                return new EBeanComparator<T>(indexes);
            }
        };
    }

    public ListCellRenderer createListCellRenderer() {
        return new DefaultListCellRenderer();
    }

    public static class EBeanComparator<T extends EBean> implements Comparator<T> {

        private int[] orderBy;

        /** Creates a new instance of ComparatorBasic */
        public EBeanComparator(int[] orderBy) {
            this.orderBy = orderBy;
        }
        
        public int compare(T o1, T o2) {
            for (int i = 0; i < orderBy.length; i++) {
                Comparable c1 = getByIndex(o1, orderBy[i]);
                Comparable c2 = getByIndex(o2, orderBy[i]);
                int result;
                if (c1 == null) {
                    if (c2 == null) {
                        result = 0;
                    } else {
                        result = -1;
                    }
                } else if (c2 == null) {
                    result = +1;
                } else {
                    result = c1.compareTo(c2);
                }
                if (result != 0) {
                    return result;
                }
            }
            return 0;
        }

        protected Comparable getByIndex(T obj, int i) {
            if (obj == null) {
                return null;
            } else {
                switch(i) {
                case 0: return obj.getIdentifier();
                case 1: return obj.getCreated();
                case 2: return obj.getUpdated();
                default: return null;
                }
            }
        }
    }
}
