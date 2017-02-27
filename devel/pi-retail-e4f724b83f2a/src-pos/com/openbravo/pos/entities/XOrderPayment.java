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

package com.openbravo.pos.entities;

import com.openbravo.data.json.JSONFormats;
import com.openbravo.data.json.JSONParam;
import com.openbravo.format.Formats;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public abstract class XOrderPayment implements JSONParam {

    private EOrder order = null;
    private double total = 0.0;
    private ETerminalPayment terminalPayment = null;

    public void writeJSON(JSONObject json) throws JSONException {
        json.put("order", getOrder().getId());
        json.put("paymentDate", JSONFormats.DATE.writeJSON(getOrder().getOrderDate()));
        json.put("docType", getTerminalPayment().getDocumentType());
        json.put("account", getTerminalPayment().getFinancialAccount());
        json.put("paymentMethod", getTerminalPayment().getPaymentMethod());
        json.put("amount", getTotal());
    }

    public final String getSearchKey() {
        return terminalPayment == null ? null : terminalPayment.getSearchKey();
    }

    public final String printTotal() {
        return Formats.CURRENCY.formatValue(new Double(getTotal()));
    }

    public final void setTotal(double total) {
        this.total = total;
    }

    public final double getTotal() {
        return total;
    }

    /**
     * @return the terminalPayment
     */
    public final ETerminalPayment getTerminalPayment() {
        return terminalPayment;
    }

    /**
     * @param terminalPayment the terminalPayment to set
     */
    public final void setTerminalPayment(ETerminalPayment terminalPayment) {
        this.terminalPayment = terminalPayment;
    }

    /**
     * @return the order
     */
    public EOrder getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(EOrder order) {
        this.order = order;
    }
}
