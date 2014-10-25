/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Itementity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Jason
 */
@Stateless
@Path("entity.itementity")
public class ItementityFacadeREST extends AbstractFacade<Itementity> {
    @PersistenceContext(unitName = "WebService_MobilePU")
    private EntityManager em;

    public ItementityFacadeREST() {
        super(Itementity.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Itementity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Itementity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Itementity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Itementity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Itementity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("items")
    @Produces({"application/json"})
    public List<Itementity> listAllItems() {
        Query q = em.createQuery("Select s from Itementity s where s.isdeleted=FALSE");
        List<Itementity> list = q.getResultList();
        for (Itementity i : list) {
            em.detach(i);
            i.setFurnitureentity(null);
            i.setItemCountryentityList(null);
            i.setLineitementityList(null);
            i.setRetailproductentity(null);
            i.setShoppinglistentityList(null);
            i.setWarehousesId(null);
            i.setWishlistentityList(null);
        }
        List<Itementity> list2 = new ArrayList();
        list2.add(list.get(0));
        return list;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
