/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adrian
 */
public class StringsTest {

    public StringsTest() {
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
  public void getHQLQuery() {

    System.out.println(replaceAll("estos uncambio $clientCriteria claro", "$clientCriteria", " (and $$$$pepe=1 ) "));
    System.out.println(replaceAll("estos uncambio ppp.$clientCriteria claro", "$clientCriteria", " (and $$$$pepe=1 ) "));
    System.out.println(replaceAll("estos uncambio p.$clientCriteria claro j.$clientCriteria", "$clientCriteria", " (and $$$$pepe=1 ) "));

  }

  private String replaceAll(String s, String search, String replacement) {
    String news = s;
    int i = news.indexOf(search);
    while (i >= 0) {
      int alias = findalias(news, i);
      if (alias >= 0) {
        news = news.substring(0, alias) + replacement.replaceAll("\\$\\$\\$\\$", news.substring(alias, i))
            + news.substring(i + search.length());
      } else {
        news = news.substring(0, i) + replacement.replaceAll("\\$\\$\\$\\$", "")
            + news.substring(i + search.length());
      }

      i = news.indexOf(search);
    }
    return news;
  }

  private int findalias(String sentence, int position) {

    int i = position - 1;
    int s = 0;

    while (i > 0) {
      char c = sentence.charAt(i);
      if (s == 0) {
        if (c == '.') {
          s = 1;
        } else {
          return -1;
        }
      } else if (s == 1) {
        if (Character.isLetterOrDigit(c)) {
          s = 2;
        } else {
          return -1;
        }
      } else if (s == 2) {
        if (!Character.isLetterOrDigit(c)) {
          if (Character.isWhitespace(c) || c == ')') {
            return i + 1;
          } else {
            return -1;
          }
        }
      }
      i--;
    }
    return -1;
  }

}