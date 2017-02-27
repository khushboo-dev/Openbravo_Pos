/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.data.json;

import com.openbravo.data.loader.QBFCompareEnum;

/**
 *
 * @author adrian
 */
public class QBFParam {

    private String field;
    private QBFCompareEnum compare;
    private HQLParam hqlparam;

    public QBFParam(String field, Object[] qbf, int iqbf, JSONFormats format) {
        this.field = field;
        this.hqlparam = new HQLParam(field.replaceAll("\\.", ""), qbf[iqbf * 2 + 1], format);
        this.compare = (QBFCompareEnum) qbf[iqbf * 2];
    }

    public QBFParam(String field, QBFCompareEnum compare, Object value, JSONFormats format) {
        this.field = field;
        this.hqlparam = new HQLParam(field.replaceAll("\\.", ""), value, format);
        this.compare = compare;
    }

    /**
     * @return the hqlparam
     */
    public HQLParam getHqlparam() {
        return hqlparam;
    }

    /**
     * @param hqlparam the hqlparam to set
     */
    public void setHqlparam(HQLParam hqlparam) {
        this.hqlparam = hqlparam;
    }

    /**
     * @return the compare
     */
    public QBFCompareEnum getCompare() {
        return compare;
    }

    /**
     * @param compare the compare to set
     */
    public void setCompare(QBFCompareEnum compare) {
        this.compare = compare;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }
}
