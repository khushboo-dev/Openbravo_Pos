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

package com.openbravo.pos.forms;

import com.openbravo.basic.BasicException;
import com.openbravo.data.loader.BatchSentence;
import com.openbravo.data.loader.BatchSentenceResource;
import com.openbravo.data.loader.Session;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 *
 * @author adrian
 */
public class ExecuteScript {
    
     private static final Logger logger = Logger.getLogger("com.openbravo.pos.forms.ExecuteScript");

    public static void main (final String args[]) {

        AppConfig config = new AppConfig(args);
        config.load();
        // Set Openbravo parameters
        String client = "FF8080812AFBCB14012AFBD3E373001F"; // F&B International Group --
        String organization = "4F68EB1C1B734E79B27DE9D2DF56089F"; // F&B Spain --
        String user = "100";
        
        try {
            Session session = AppViewConnection.createSession(config);

            BatchSentence bsentence = new BatchSentenceResource(session, "/com/openbravo/pos/scripts/PostgreSQL-integrated-data.sql");
            bsentence.putParameter("APP_ID", Matcher.quoteReplacement(AppLocal.APP_ID));
            bsentence.putParameter("APP_NAME", Matcher.quoteReplacement(AppLocal.APP_NAME));
            bsentence.putParameter("APP_VERSION", Matcher.quoteReplacement(AppLocal.APP_VERSION));
            bsentence.putParameter("CONFIG_SYSTEMCLIENT", Matcher.quoteReplacement("0"));
            bsentence.putParameter("CONFIG_CLIENT", Matcher.quoteReplacement(client));
            bsentence.putParameter("CONFIG_SUPERORG", Matcher.quoteReplacement("0"));
            bsentence.putParameter("CONFIG_ORG", Matcher.quoteReplacement(organization));
            bsentence.putParameter("CONFIG_USER", Matcher.quoteReplacement(user));

            java.util.List l = bsentence.list();

            if (l.size() > 0) {
                logger.log(Level.SEVERE, "Errors executing");
                for (Object o: l) {
                    logger.log(Level.INFO, o.toString());
                }
            } else {
                logger.info("Success");
            }
        } catch (BasicException e) {
            logger.log(Level.SEVERE, "Cannot connect", e);
        }
    }

}
