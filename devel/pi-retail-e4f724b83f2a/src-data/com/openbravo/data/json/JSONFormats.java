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

import java.io.UnsupportedEncodingException;
import java.text.*;
import java.util.Date;
import com.openbravo.pos.util.Base64Encoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONObject;

public abstract class JSONFormats<T> {

    private static final Logger logger = Logger.getLogger(JSONFormats.class.getName());

    public final static JSONFormats<String> STRING = new JSONFormatsSTRING();
    public final static JSONFormats<Integer> INTEGER = new JSONFormatsINTEGER();
    public final static JSONFormats<Double> DOUBLE = new JSONFormatsDOUBLE();
    public final static JSONFormats<Date> TIMESTAMP = new JSONFormatsTIMESTAMP();
    public final static JSONFormats<Date> DATE = new JSONFormatsDATE();
    public final static JSONFormats<byte[]> BYTEA = new JSONFormatsBYTEA();
    public final static JSONFormats<String> LONGSTRING = new JSONFormatsLONGSTRING();

    private static DateFormat jsdatetimeformattz = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    private static DateFormat jsdatetimeformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static DateFormat jsdateformat = new SimpleDateFormat("yyyy-MM-dd");


    /** Creates a new instance of Formats */
    protected JSONFormats() {
    }

    public Object writeJSON(T value) {
        if (value == null) {
            return JSONObject.NULL;
        } else {
            return formatValueInt(value);
        }
    }

    public T parseValue(Object value, T defvalue) {
        if (isEmptyOrNull(value)) {
            return defvalue;
        } else {
            return parseValueInt(value);
        }
    }

    public T readJSON(Object value) {
        return parseValue(value, null);
    }

    private static boolean isEmptyOrNull(Object value) {
        if (value == null) {
          return true;
        } else if (JSONObject.NULL.equals(value)) {
            return true;
        } else if (value instanceof String && ((String) value).trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    protected abstract Object formatValueInt(T value);
    protected abstract T parseValueInt(Object value);
    protected abstract String getName();

    private static final class JSONFormatsSTRING extends JSONFormats<String> {
        protected Object formatValueInt(String value) {

            return value;
        }
        protected String parseValueInt(Object value) {
            return value.toString();
        }
        protected String getName() {
            return "string";
        }
    }

    private static final class JSONFormatsTIMESTAMP extends JSONFormats<Date> {
        protected Object formatValueInt(Date value) {
            String result = jsdatetimeformattz.format(value);

            // fix value
            if (result.length() > 4) {
                final int length = result.length();
                // must end with +???? or -????
                if (result.charAt(length - 5) == '-' || result.charAt(length - 5) == '+') {
                    result = result.substring(0, length - 2) + ":" + result.substring(length - 2);
                } else {
                    result += "+00:00";
                }
            }

            return result;
        }
        protected Date parseValueInt(Object value) {

            String myvalue = value.toString();

             try {
                // fix value
                if (myvalue.length() > 5) {
                    final int length = myvalue.length();
                    // must end with +??:?? or -??:??
                    if (myvalue.charAt(length - 3) == ':' && (myvalue.charAt(length - 6) == '-' || myvalue.charAt(length - 6) == '+')) {
                        return jsdatetimeformattz.parse(myvalue.substring(0, length - 3) + myvalue.substring(length - 2));
                    } else {
                        return jsdatetimeformat.parse(myvalue.substring(0, length - 3) + myvalue.substring(length - 2));
                    }
                } else {
                    logger.log(Level.SEVERE, "Date value too short");
                    throw new NumberFormatException("Date value too short");
                 }
            } catch (ParseException ex) {
                try {
                    // 2nd chance as date without time.
                    return jsdateformat.parse(myvalue);
                } catch (ParseException ex1) {
                    logger.log(Level.SEVERE, null, ex); // logging first exception better
                    throw new NumberFormatException(ex.getMessage());
                }
            }
        }
        protected String getName() {
            return "datetime";
        }
    }

    private static final class JSONFormatsDATE extends JSONFormats<Date> {
        protected Object formatValueInt(Date value) {
            return jsdateformat.format(value);
        }
        protected Date parseValueInt(Object value) {
            try {
                return jsdateformat.parse(value.toString());
            } catch (ParseException ex) {
                logger.log(Level.SEVERE, null, ex);
                throw new NumberFormatException(ex.getMessage());
            }
        }
        protected String getName() {
            return "date";
        }
    }

    private static final class JSONFormatsBYTEA extends JSONFormats<byte[]> {
        protected Object formatValueInt(byte[] value) {
            return Base64Encoder.encode(value);
        }
        protected byte[] parseValueInt(Object value) {
            return Base64Encoder.decode(value.toString());
        }
        protected String getName() {
            return "binary";
        }
    }
    private static final class JSONFormatsLONGSTRING extends JSONFormats<String> {
        protected Object formatValueInt(String value) {
            try {
                return Base64Encoder.encode(value.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }
        }
        protected String parseValueInt(Object value) {
            try {
                return new String(Base64Encoder.decode(value.toString()), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                logger.log(Level.SEVERE, null, ex);
                return null;
            }
        }
        protected String getName() {
            return "binary";
        }
    }
    private static final class JSONFormatsINTEGER extends JSONFormats<Integer> {
        protected Object formatValueInt(Integer value) {
            return value;
        }
        protected Integer parseValueInt(Object value) {
            return Integer.valueOf(value.toString());
        }
        protected String getName() {
            return "long";
        }
    }
    private static final class JSONFormatsDOUBLE extends JSONFormats<Double> {
        protected Object formatValueInt(Double value) {
            return value;
        }
        protected Double parseValueInt(Object value) {
            return Double.valueOf(value.toString());
        }
        protected String getName() {
            return "bigdecimal";
        }
    }
}
