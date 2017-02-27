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
import com.openbravo.data.user.ListProvider;
import java.util.List;

/**
 *
 * @author adrian
 */
public class HQLListProvider<T>  implements ListProvider<T> {

    private ServiceHQL<T> service;
    private HQLParamsCreator paramscreator;
    private HQLParam[] params;

    public HQLListProvider(ServiceHQL<T> service, HQLParamsCreator paramscreator) {
        this.service = service;
        this.paramscreator = paramscreator;
        this.params = null;
    }

    public HQLListProvider(ServiceHQL service) {
        this(service, null);
    }

    public List<T> loadData() throws BasicException {
        params = (paramscreator == null) ? null : paramscreator.create();
        return refreshData();
    }

    public List<T> refreshData() throws BasicException {
        return service.list(params);
    }
}
