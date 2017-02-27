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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */

public class ServiceQBF<T extends EBean> {

    private static final Logger logger = Logger.getLogger(ServiceQBF.class.getName());

    private HttpSession httpsession;
    private ClassEBean<T> bean;
    private String qbf;

    public ServiceQBF(HttpSession httpsession, ClassEBean<T> bean, String qbf) {
        this.httpsession = httpsession;
        this.bean = bean;
        this.qbf = qbf;
    }
    public List<T> list(Object[] params) throws BasicException {

        String hql;
        HQLParam[] finalhqlparams;
        List<HQLParam> lhqlparams = new ArrayList<HQLParam>();

        if (params == null || params.length == 0) {
            hql = qbf.replace("$qbffilter", " (1=1) ");         
        } else {
            StringBuilder qbffilter = new StringBuilder();
            
            for (Object p : params) {

                if (p instanceof QBFParam) {
                    QBFParam pqbf = (QBFParam) p;
                    String exp = pqbf.getCompare().getHQLExpression(pqbf.getField(), pqbf.getHqlparam().getName());
                    if (exp != null) {
                        if (qbffilter.length() > 0) {
                            qbffilter.append(" and ");
                        }
                        qbffilter.append(exp);
                        if (pqbf.getCompare().isHQLValue()) {
                            lhqlparams.add(pqbf.getHqlparam());
                        }
                    }
                } else if (p instanceof HQLParam) {
                   lhqlparams.add((HQLParam) p);
                }
            }

            if (qbffilter.length() == 0) {
                hql = qbf.replace("$qbffilter", " (1=1) ");
            } else {
                hql = qbf.replace("$qbffilter", " (" + qbffilter.toString() + ") ");
            }
        }

        ServiceHQL<T> service = new ServiceHQL<T>(httpsession, bean, hql);
        return service.list(lhqlparams.toArray(new HQLParam[lhqlparams.size()]));
    }
}
