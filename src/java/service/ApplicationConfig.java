/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Jason
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(service.CountryentityFacadeREST.class);
        resources.add(service.ECommerceFacadeREST.class);
        resources.add(service.FurnitureentityFacadeREST.class);
        resources.add(service.ItementityFacadeREST.class);
        resources.add(service.LineitementityFacadeREST.class);
        resources.add(service.LoyaltytierentityFacadeREST.class);
        resources.add(service.MemberentityFacadeREST.class);
        resources.add(service.QrphonesyncentityFacadeREST.class);
        resources.add(service.RetailproductentityFacadeREST.class);
        resources.add(service.StoreentityFacadeREST.class);
        resources.add(service.WishlistentityFacadeREST.class);
    }
    
}
