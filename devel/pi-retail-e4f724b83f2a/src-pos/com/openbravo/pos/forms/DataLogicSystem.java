//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
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

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import javax.imageio.ImageIO;
import com.openbravo.basic.BasicException;
import com.openbravo.data.json.JSONReadString;
import com.openbravo.data.json.ServiceFind;
import com.openbravo.data.json.ServiceHQL;
import com.openbravo.data.json.ServicePut;
import com.openbravo.data.loader.*;
import com.openbravo.format.Formats;
import com.openbravo.pos.entities.EBusinessPartner;
import com.openbravo.pos.entities.EBusinessPartnerLocation;
import com.openbravo.pos.entities.ELocation;
import com.openbravo.pos.entities.EPriceList;
import com.openbravo.pos.entities.EProduct;
import com.openbravo.pos.entities.EUser;
import com.openbravo.pos.entities.EResource;
import com.openbravo.pos.entities.ETax;
import com.openbravo.pos.entities.ETerminal;
import com.openbravo.pos.entities.ETerminalPayment;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrianromero
 */
public class DataLogicSystem extends BeanFactoryDataSingle {
    
    protected String m_sInitScript;
    private ServiceHQL<ETerminal> m_version;
    
    protected ServiceHQL<EUser> m_peoplevisible;
    
    private SentenceExec m_changepassword;
    
    private ServiceHQL<EResource> m_resourcebytes;
    private ServicePut<EResource> m_resourcebytesput;
   
    private Map<String, EResource> resourcescache;
    
    /** Creates a new instance of DataLogicSystem */
    public DataLogicSystem() {            
    }
    
    public void init(Session s){

        m_sInitScript = "/com/openbravo/pos/scripts/" + s.DB.getName();

        m_version = new ServiceHQL<ETerminal>(App.getInstance().getHttpSession(), ETerminal.getClassEBean(), "where searchKey = :parameter0");

        m_peoplevisible = new ServiceHQL<EUser>(App.getInstance().getHttpSession(), EUser.getClassEBean(), "order by name");

        m_resourcebytes = new ServiceHQL(App.getInstance().getHttpSession(), EResource.getClassEBean(), "where name = :parameter0");
        m_resourcebytesput = new ServicePut(App.getInstance().getHttpSession(), EResource.getClassEBean());
        
        m_changepassword =  new PreparedSentence(s
                , "UPDATE OBPOS_PEOPLE SET APPPASSWORD = ? WHERE OBPOS_PEOPLE_ID = ?"
                ,new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING}));
        
        resetResourcesCache();        
    }


    public String getInitScript() {
        return m_sInitScript;
    }
    
//    public abstract BaseSentence getShutdown();
    
    public final ETerminal findTerminal(String terminal) throws BasicException {
        return m_version.find(terminal);
    }

    public final List<EUser> listPeopleVisible() throws BasicException {
        return m_peoplevisible.list();
    }

    public final EUser findPeopleByName(String username) throws BasicException {
        return new ServiceHQL<EUser>(App.getInstance().getHttpSession(), EUser.getClassEBean(), "where name=:parameter0").find(username);
    }   

    public final void execChangePassword(Object[] userdata) throws BasicException {
        m_changepassword.exec(userdata);
    }
    
    public final void resetResourcesCache() {
        resourcescache = new HashMap<String, EResource>();
    }

    public EResource getEResource(String name) throws BasicException {
        return m_resourcebytes.find(name);
    }

    public void setEResource(EResource res) throws BasicException {
        m_resourcebytesput.exec(res);
        resourcescache.put(res.getName(), res);
    }

    public final byte[] getResourceAsBinary(String name) {

        EResource resource = resourcescache.get(name);
        if (resource == null) {
            try {
                resource = getEResource(name);
            } catch (BasicException e) {
            }
            if (resource == null) {
                resource = new EResource();
            }
            resourcescache.put(name, resource);
        }
        return resource.getContent();
    }
    
    public final String getResourceAsText(String name) {
        return Formats.BYTEA.formatValue(getResourceAsBinary(name));
    }
    
    public final String getResourceAsXML(String name) {
        return Formats.BYTEA.formatValue(getResourceAsBinary(name));
    }    
    
    public final BufferedImage getResourceAsImage(String name) {
        try {
            byte[] img = getResourceAsBinary(name); // , ".png"
            return img == null ? null : ImageIO.read(new ByteArrayInputStream(img));
        } catch (IOException e) {
            return null;
        }
    }
    
    public List<ETerminalPayment> loadTerminalPayments(String terminal) throws BasicException {
        return new ServiceHQL<ETerminalPayment>(App.getInstance().getHttpSession(), ETerminalPayment.getClassEBean(),
                "where obposApplications.id = :parameter0").
                list(terminal);        
    }

    public EBusinessPartner loadBusinessPartner(String id) throws BasicException {
        return new ServiceFind<EBusinessPartner>(App.getInstance().getHttpSession(), EBusinessPartner.getClassEBean()).find(id);
    }

    public EBusinessPartnerLocation findBusinessPartnerLocation(String businessPartner) throws BasicException {
        return new ServiceHQL<EBusinessPartnerLocation>(App.getInstance().getHttpSession(), EBusinessPartnerLocation.getClassEBean(),
                "from BusinessPartnerLocation where id = (select min(id) from BusinessPartnerLocation where businessPartner.id = :parameter0 and $readableCriteria)").
                find(businessPartner);
    }

    public ELocation findOrganizationLocation(String org) throws BasicException {
        return new ServiceHQL<ELocation>(App.getInstance().getHttpSession(), ELocation.getClassEBean(),
                "from Location where id = (select min(locationAddress) from OrganizationInformation where organization.id = :parameter0 and $readableCriteria)").
                find(org);
    }

    public EPriceList getPriceList(String pricelist) throws BasicException {
        return new ServiceFind<EPriceList>(App.getInstance().getHttpSession(), EPriceList.getClassEBean()).find(pricelist);
    }

    public String getPriceListVersion(String pricelist) throws BasicException {
        return new ServiceHQL<String>(App.getInstance().getHttpSession(), new JSONReadString("id"),
                "select plv.id AS id from PricingPriceListVersion AS plv where plv.$readableCriteria and plv.priceList.id =:parameter0 and "
                + "plv.validFromDate = (select max(pplv.validFromDate) from PricingPriceListVersion as pplv where pplv.priceList.id = :parameter0)").
                find(pricelist);
    }

    public final EProduct getProductInfoById(String pricelistversion, String id) throws BasicException {
        return new ServiceHQL<EProduct>(App.getInstance().getHttpSession(), EProduct.getClassEBean(), "where priceListVersion.id = :parameter0 and id = :parameter1").find(new String[]{pricelistversion, id});
    }

    public final List<ETax> getTaxList(String country) throws BasicException {
        return new ServiceHQL<ETax>(App.getInstance().getHttpSession(), ETax.getClassEBean(),
                "where salesPurchaseType='S' and country.id=:parameter0 and (destinationCountry.id=:parameter0 or destinationCountry=null) and destinationRegion=null order by name")
                .list(country);
    }
}
