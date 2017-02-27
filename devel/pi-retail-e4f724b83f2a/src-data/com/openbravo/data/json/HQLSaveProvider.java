//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2011 Openbravo, S.L.
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

package com.openbravo.data.json;

import com.openbravo.basic.BasicException;
import com.openbravo.data.user.SaveProvider;

/**
 *
 * @author adrian
 */
public class HQLSaveProvider<T extends EBean> implements SaveProvider<T> {

    private HttpSession httpsession;
    private ClassEBean<T> clazz;

    public HQLSaveProvider(HttpSession httpsession, ClassEBean<T> clazz) {
        this.httpsession = httpsession;
        this.clazz = clazz;
    }

    public boolean canDelete() {
        return true;
    }

    public int deleteData(T value) throws BasicException {
        new ServiceDelete<T>(httpsession, clazz).exec(value.getId());
        return 1;
    }

    public boolean canSave() {
        return true;
    }

    public T saveData(T value) throws BasicException {
        return new ServicePut<T>(httpsession, clazz).exec(value);
    }
}
