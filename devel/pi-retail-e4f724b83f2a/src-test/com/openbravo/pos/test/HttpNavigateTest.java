/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.test;

import com.openbravo.data.json.HttpNavigate;
import com.openbravo.data.json.HttpSession;
import java.io.IOException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class HttpNavigateTest {

    public HttpNavigateTest() {
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
     public void hello() throws IOException, JSONException {

        HttpNavigate http = new HttpNavigate(new HttpSession("http://localhost:8080/openbravo", "Openbravo", "openbravo"), HttpNavigate.JSONDEFAULT);



        JSONObject result = http.navigate("GET", "/OBPOS_Applications/openbravopos");
        System.out.println(result);

        Assert.assertEquals("openbravopos", result.get("id"));
        Assert.assertEquals("2.30.2", result.get("version"));
     }

}