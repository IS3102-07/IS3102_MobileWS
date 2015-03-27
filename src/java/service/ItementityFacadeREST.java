/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.FurnitureHelper;
import Entity.ItemCountryentity;
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
import javax.ws.rs.QueryParam;

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

//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Itementity> findAll() {
//        return super.findAll();
//    }
//
//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Itementity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
    @GET
    @Path("items")
    @Produces({"application/json"})
    public List<Itementity> listAllItemsByCountry(@QueryParam("country") String country) {
        Query q = em.createQuery("Select c from ItemCountryentity c,Countryentity co where c.countryId.id=co.id and c.countryId.name=:country and c.isdeleted=false");
        q.setParameter("country", country);
        List<ItemCountryentity> list = q.getResultList();
        List<Itementity> itemList = new ArrayList();
        for (ItemCountryentity itemCountry : list) {
            Itementity item = itemCountry.getItemId();
            if (!item.getIsdeleted()) {
                em.detach(item);
                item.setFurnitureentity(null);
                item.setItemCountryentityList(null);
                item.setLineitementityList(null);
                item.setRetailproductentity(null);
                item.setWarehousesId(null);
                item.setWishlistentityList(null);
                itemList.add(item);
            }
        }
        return itemList;
    }

    @GET
    @Path("itemname")
    @Produces({"application/json"})
    public String getItemNameBySKU(@QueryParam("SKU") String SKU) {
        try {
            Query q = em.createQuery("Select i from Itementity i where i.sku=:SKU and i.isdeleted=false");
            q.setParameter("SKU", SKU);
            Itementity item = (Itementity) q.getSingleResult();
            return item.getName();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    @GET
    @Path("item")
    @Produces({"application/json"})
    public ItemCountryentity getItemBySKU(@QueryParam("SKU") String SKU) {
        try {
            Query q = em.createQuery("Select i from ItemCountryentity i where i.sku=:SKU and i.isdeleted=false");
            q.setParameter("SKU", SKU);
            ItemCountryentity item = (ItemCountryentity) q.getSingleResult();
            return item;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GET
    @Path("furniture")
    @Produces({"application/json"})
    public List<FurnitureHelper> listAllFurnitureByCountry(@QueryParam("country") String country) {
        Query q = em.createQuery("Select c from ItemCountryentity c,Countryentity co where c.countryId.id=co.id and co.name=:country and c.isdeleted=false");
        
        q.setParameter("country", country);
        List<ItemCountryentity> list = q.getResultList();
        List<FurnitureHelper> furnitureList = new ArrayList();
        System.out.println("Country is " + country);
        System.out.println("Number of furniture retreived: " + list.size());
        for (ItemCountryentity itemCountry : list) {
            Itementity item = itemCountry.getItemId();
            if (!item.getIsdeleted() && item.getType().equals("Furniture")) {
                FurnitureHelper furnitureHelper = new FurnitureHelper();
                furnitureHelper.setId(item.getId());
                furnitureHelper.setName(item.getName());
                furnitureHelper.setDescription(item.getDescription());
                furnitureHelper.setImageUrl(item.getFurnitureentity().getImageurl());
                furnitureHelper.setSKU(item.getSku());
                furnitureHelper.setType(item.getType());
                furnitureHelper.setLength(item.getLength());
                furnitureHelper.setWidth(item.getWidth());
                furnitureHelper.setHeight(item.getHeight());
                furnitureList.add(furnitureHelper);
            }
        }
        return furnitureList;
    }

//    @GET
//    @Path("itemInfo")
//    @Produces({"application/json"})
//    public FurnitureHelper getItemInfo(@QueryParam("country") Long itemId) {
//        Query q = em.createQuery("Select i from Itementity i where i.id=:itemId and i.isdeleted=false");
//        List<Itementity> item = q.getSingleResult();
//        List<FurnitureHelper> furnitureList = new ArrayList();
//        
//        FurnitureHelper furnitureHelper = new FurnitureHelper();
//        furnitureHelper.setId(item.getId());
//        furnitureHelper.setName(item.getName());
//        furnitureHelper.setDescription(item.getDescription());
//        furnitureHelper.setImageUrl(item.getFurnitureentity().getImageurl());
//        furnitureHelper.setSKU(item.getSku());
//        furnitureHelper.setType(item.getType());
//        furnitureList.add(furnitureHelper);
//        
//        return furnitureList;
//    }
//    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
