//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2011 Openbravo, S.L.
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

/**
 *
 * @author adrian
 */
public class HttpSession {

    private String URL;
    private String user;
    private String password;

    public HttpSession(String URL, String user, String password) {
        this.URL = URL;
        this.user = user;
        this.password = password;
    }

    public HttpSession(String URL) {
        this(URL, null, null);
    }

    public void setCredentials(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * @return the URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
