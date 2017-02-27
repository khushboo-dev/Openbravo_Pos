/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.test;

import com.openbravo.basic.BasicException;
import com.openbravo.data.json.JSONFormats;
import java.util.Date;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class JSONMiscTest {

    public JSONMiscTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

     @Test
     public void hello() throws JSONException {
        JSONObject json = new JSONObject();
        // System.out.println(json.getJSONObject("pepe"));
     }

     @Test
     public void date() throws JSONException, BasicException {
        JSONObject json = new JSONObject();
        json.put("newdate", new Date());
        json.put("coco", "2011-10-13T13:28:10+02:00");
//        System.out.println(json.toString());
//        System.out.println(convertFromXSDToJavaFormat( "2011-10-13T17:09:58+02:00"));
        System.out.println(JSONFormats.TIMESTAMP.writeJSON(new Date()));
        System.out.println(JSONFormats.TIMESTAMP.readJSON("2011-10-13T17:09:58+02:00"));

        
     }

     @Test
     public void booleans() throws JSONException, BasicException {

        JSONObject json = new JSONObject();
        json.put("active", "null");
        json.put("count", "null");

        System.out.println(json.optBoolean("active"));
        System.out.println(json.optString("count"));
     }

  public static String convertFromXSDToJavaFormat(String dateValue) {
    if (dateValue == null || dateValue.length() < 3) {
      return dateValue;
    }
    final int length = dateValue.length();
    // must end with +??:?? or -??:??
    if (dateValue.charAt(length - 3) == ':'
        && (dateValue.charAt(length - 6) == '-' || dateValue.charAt(length - 6) == '+')) {
      final String result = dateValue.substring(0, length - 3) + dateValue.substring(length - 2);
      return result;
    }
    // make them utc, the timezone must be there
    return dateValue + "+0000";
  }
}