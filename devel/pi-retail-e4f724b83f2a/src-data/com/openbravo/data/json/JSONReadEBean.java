/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.data.json;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class JSONReadEBean<T extends EBean> implements JSONRead<T>{

    private ClassEBean<T> clazz;

    public JSONReadEBean(ClassEBean<T> clazz) {
        this.clazz = clazz;
    }

    public T readValues(JSONObject json) throws JSONException {
        return clazz.fromJSON(json);
    }

}
