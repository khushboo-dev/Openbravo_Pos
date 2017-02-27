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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class HttpNavigate {

    private static final Logger logger = Logger.getLogger(ServiceFind.class.getName());

    public static final String JSONDEFAULT = "/org.openbravo.service.json.jsonrest";
    public static final String JSONTERMINAL = "/org.openbravo.service.retail.posterminal.jsonrest";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    private String service;
    private HttpSession httpsession;

    public HttpNavigate(HttpSession httpsession, String service) {
        this.httpsession = httpsession;
        this.service = service;
    }

    public JSONObject navigate(String method, String urladdress) throws IOException {
        return navigate(method, urladdress, null, null);
    }

    public JSONObject navigate(String method, String urladdress, HttpParam[] params) throws IOException {
        return navigate(method, urladdress, params, null);
    }

    public JSONObject navigate(String method, String urladdress, String data) throws IOException {
        return navigate(method, urladdress, null, data);
    }

    public JSONObject navigate(String method, String urladdress, HttpParam[] params, String data) throws IOException {

        URL url;
        BufferedReader readerin = null;
        Writer writerout = null;
        try {

            StringBuilder base = new StringBuilder();
            base.append(httpsession.getURL());
            base.append(service);
            base.append(urladdress);

            logger.log(Level.INFO, "Executing service: {0}{1}, Method: {2}", new String[]{service, urladdress, method});

            int getcount = 0;
            if (params != null) {
                for (HttpParam p : params) {
                    appendSeparator(base, getcount++);
                    append(base, p);
                }
            }

            // adding credentials. TO DO: Replace by previous authentification and cookie.
            appendSeparator(base, getcount++);
            append(base, new HttpParam("l", httpsession.getUser()));
            appendSeparator(base, getcount++);
            append(base, new HttpParam("p", httpsession.getPassword()));

            url = new URL(base.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method); // GET, POST, ...
            connection.setAllowUserInteraction(false);

            connection.setUseCaches(false);
            connection.setDoInput(true);

            if (data != null && !data.equals("")) {
                logger.log(Level.INFO, "Executing service: {0}{1}, posting: {2}", new String[]{service, urladdress, data});

                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
                connection.setDoOutput(true);

                writerout = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
//                writerout.write("Content-Type: application/x-www-form-urlencoded,encoding=UTF-8\n");
//                writerout.write("Content-length: " + String.valueOf(data.length()) + "\n");
//                writerout.write("\n");
                writerout.write(data);
                writerout.flush();

                writerout.close();
                writerout = null;
            } else {
                connection.setDoOutput(false);
            }

            int responsecode = connection.getResponseCode();
            if (responsecode == HttpURLConnection.HTTP_OK) {
                StringBuilder text = new StringBuilder();

                readerin = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = null;
                while ((line = readerin.readLine()) != null) {
                    text.append(line);
                    text.append(System.getProperty("line.separator"));
                }

                return new JSONObject(text.toString());
            } else {
                throw new IOException(MessageFormat.format("message.httperror {0}", Integer.toString(responsecode), connection.getResponseMessage()));
            }
        } catch (JSONException e) {
            throw new IOException("JSON exception", e);
        } finally {
            if (writerout != null) {
                try {
                    writerout.close();
                } catch (IOException ex) {
                }
                writerout = null;
            }
            if (readerin != null) {
                try {
                    readerin.close();
                } catch (IOException ex) {
                }
                readerin = null;
            }
        }
    }

    private void append(StringBuilder s, HttpParam param) throws UnsupportedEncodingException {
        s.append(URLEncoder.encode(param.getName(), "UTF-8"));
        s.append('=');
        s.append(URLEncoder.encode(param.getValue(), "UTF-8"));
    }

    private void appendSeparator(StringBuilder s, int index) throws UnsupportedEncodingException {
        s.append(index == 0 ? '?' : '&');
    }
}
