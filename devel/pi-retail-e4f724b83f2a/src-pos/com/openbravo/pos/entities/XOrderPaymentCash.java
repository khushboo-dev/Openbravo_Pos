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

import com.openbravo.format.Formats;

public class XOrderPaymentCash extends XOrderPayment {
    
    private double paid;
    
    /** Creates a new instance of PaymentInfoCash */
    public XOrderPaymentCash(ETerminalPayment terminalPayment, double total, double paid) {
        setTerminalPayment(terminalPayment);
        setTotal(total);
        this.paid = paid;
    }

    public double getPaid() {
        return paid;
    }
    
    public String printPaid() {
        return Formats.CURRENCY.formatValue(getPaid());
    }

    public String printChange() {
        return Formats.CURRENCY.formatValue(getPaid() - getTotal());
    }    
}
