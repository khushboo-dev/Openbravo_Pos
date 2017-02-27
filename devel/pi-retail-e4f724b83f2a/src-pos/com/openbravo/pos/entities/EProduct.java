/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openbravo.pos.entities;

import com.openbravo.data.json.ClassEBean;
import com.openbravo.data.json.EBean;
import com.openbravo.data.json.JSONFormats;
import com.openbravo.data.loader.ImageUtils;
import com.openbravo.format.Formats;
import java.awt.image.BufferedImage;
import java.util.Properties;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author adrian
 */
public class EProduct extends EBean {

    private String reference;
    private String code;
    private String name;
    private String UOM;
    private String auxiliar;
    private boolean scale;
    private String posCategory;
    private boolean catalog;
    private Integer posLine;
    private String taxCategory;
    private String attributeSet;    
    private Properties attributes;
    private BufferedImage image;

    private double priceSell;

    private double priceBuy; // To remove
    private double stockCost; // To remove
    private double stockVolume; // To remove

    @Override
    public void writeJSON(JSONObject json) throws JSONException {
        json.put("searchKey", JSONFormats.STRING.writeJSON(getReference()));
        json.put("uPCEAN", JSONFormats.STRING.writeJSON(getCode()));
        json.put("name", getName());
        json.put("uOM", JSONFormats.STRING.writeJSON(getUOM()));
        json.put("auxiliaryProductOf", JSONFormats.STRING.writeJSON(getAuxiliar()));
        json.put("isScale", isScale());
        json.put("pOSCategory", JSONFormats.STRING.writeJSON(getPosCategory()));
        json.put("isCatalog", isCatalog());
        json.put("pOSLine", JSONFormats.INTEGER.writeJSON(getPosLine()));
        json.put("taxCategory", JSONFormats.STRING.writeJSON(getTaxCategory()));
        json.put("attributeSet", JSONFormats.STRING.writeJSON(getAttributeSet()));
        json.put("attributes", JSONFormats.STRING.writeJSON(ImageUtils.writeStringProperties(getAttributes())));
        json.put("binaryData", JSONFormats.BYTEA.writeJSON(ImageUtils.writeImage(getImage())));

        json.put("netListPrice", JSONFormats.DOUBLE.writeJSON(getPriceSell()));
    }

    @Override
    public void readJSON(JSONObject json) throws JSONException {
        setReference(JSONFormats.STRING.readJSON(json.get("searchKey")));
        setCode(JSONFormats.STRING.readJSON(json.get("uPCEAN")));
        setName(json.getString("name"));
        setUOM((JSONFormats.STRING.readJSON(json.opt("uOM"))));
        setAuxiliar((JSONFormats.STRING.readJSON(json.get("auxiliaryProductOf"))));
        setScale(json.getBoolean("isScale")); //
        setPosCategory(JSONFormats.STRING.readJSON(json.get("pOSCategory")));
        setCatalog(json.getBoolean("isCatalog"));
        setPosLine(JSONFormats.INTEGER.readJSON(json.get("pOSLine")));
        setTaxCategory(JSONFormats.STRING.readJSON(json.get("taxCategory")));
        setAttributeSet(JSONFormats.STRING.readJSON(json.get("attributeSet")));
        setAttributes(ImageUtils.readStringProperties(JSONFormats.STRING.readJSON(json.get("attributes"))));
        setImage(ImageUtils.readImage(JSONFormats.BYTEA.readJSON(json.get("binaryData"))));

        setPriceSell(JSONFormats.DOUBLE.readJSON(json.get("netListPrice")));
    }

    public static ClassEBean<EProduct> getClassEBean() {
        return new ClassEBean<EProduct>() {
            @Override
            public EProduct create() {
                return new EProduct();
            }

            @Override
            public String getEntity() {
                return "OBPOS_ProductView";
            }
        };
    }
    
    public final double getPriceSellTax(ETax tax) {
        return getPriceSell() * (1.0 + tax.getRate() / 100.0);
    }

    public String printPriceSell() {
        return Formats.CURRENCY.formatValue(new Double(getPriceSell()));
    }

    public String printPriceSellTax(ETax tax) {
        return Formats.CURRENCY.formatValue(new Double(getPriceSellTax(tax)));
    }

    public String getProperty(String key) {
        return attributes.getProperty(key);
    }

    public String getProperty(String key, String defaultvalue) {
        return attributes.getProperty(key, defaultvalue);
    }

    public void setProperty(String key, String value) {
        attributes.setProperty(key, value);
    }

    @Override
    public final String toString() {
        return reference + " - " + name;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean isAuxiliar() {
        return auxiliar != null;
    }
    
    /**
     * @return the auxiliar
     */
    public String getAuxiliar() {
        return auxiliar;
    }

    /**
     * @param auxiliar the auxiliar to set
     */
    public void setAuxiliar(String auxiliar) {
        this.auxiliar = auxiliar;
    }

    /**
     * @return the scale
     */
    public boolean isScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(boolean scale) {
        this.scale = scale;
    }

    /**
     * @return the category
     */
    public String getPosCategory() {
        return posCategory;
    }

    /**
     * @param category the category to set
     */
    public void setPosCategory(String posCategory) {
        this.posCategory = posCategory;
    }

    /**
     * @return the category
     */
    public Integer getPosLine() {
        return posLine;
    }

    /**
     * @param category the category to set
     */
    public void setPosLine(Integer posLine) {
        this.posLine = posLine;
    }
    /**
     * @return the taxCategory
     */
    public String getTaxCategory() {
        return taxCategory;
    }

    /**
     * @param taxcategory the taxcategory to set
     */
    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    /**
     * @return the attributeset
     */
    public String getAttributeSet() {
        return attributeSet;
    }

    /**
     * @param attributeset the attributeset to set
     */
    public void setAttributeSet(String attributeSet) {
        this.attributeSet = attributeSet;
    }

    /**
     * @return the pricesell
     */
    public double getPriceSell() {
        return priceSell;
    }

    /**
     * @param pricesell the pricesell to set
     */
    public void setPriceSell(double priceSell) {
        this.priceSell = priceSell;
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * @return the attributes
     */
    public Properties getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Properties attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the catalog
     */
    public boolean isCatalog() {
        return catalog;
    }

    /**
     * @param catalog the catalog to set
     */
    public void setCatalog(boolean catalog) {
        this.catalog = catalog;
    }

    /**
     * @return the UOM
     */
    public String getUOM() {
        return UOM;
    }

    /**
     * @param UOM the UOM to set
     */
    public void setUOM(String UOM) {
        this.UOM = UOM;
    }
}
