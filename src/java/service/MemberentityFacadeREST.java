/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entity.Itementity;
import Entity.Loyaltytierentity;
import Entity.MemberHelper;
import Entity.Memberentity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
@Path("entity.memberentity")
public class MemberentityFacadeREST extends AbstractFacade<Memberentity> {

    @PersistenceContext(unitName = "WebService_MobilePU")
    private EntityManager em;

    public MemberentityFacadeREST() {
        super(Memberentity.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Memberentity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Memberentity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("members")
    @Produces({"application/json"})
    public List<Memberentity> listAllMembers() {
        Query q = em.createQuery("Select s from Memberentity s where s.isdeleted=FALSE");
        List<Memberentity> list = q.getResultList();
        for (Memberentity m : list) {
            em.detach(m);
            m.setCountryId(null);
            m.setLoyaltytierId(null);
            m.setShoppinglistId(null);
            m.setWishlistId(null);
        }
        List<Memberentity> list2 = new ArrayList();
        list2.add(list.get(0));
        return list;
    }

    @GET
    @Path("login")
    @Produces("application/json")
    public Memberentity loginMember(@QueryParam("email") String email, @QueryParam("password") String password) {
        System.out.println("loginMember is called");
        try {
            Query q = em.createQuery("SELECT m FROM Memberentity m where m.email=:email and m.isdeleted=FALSE");
            q.setParameter("email", email);
            Memberentity m = (Memberentity) q.getSingleResult();
            System.out.println("member email: " + m.getEmail());
            String passwordSalt = m.getPasswordsalt();
            String passwordHash = generatePasswordHash(passwordSalt, password);
            if (passwordHash.equals(m.getPasswordhash())) {
                em.detach(m);
                m.setLoyaltytierId(null);
                m.setCity(null);
                m.setAccountactivationstatus(null);
                m.setAccountlockstatus(null);
                m.setAddress(null);
                m.setPasswordhash(null);
                m.setShoppinglistId(null);
                m.setWishlistId(null);
                m.setPasswordhash(null);
                m.setPasswordsalt(null);
                m.setPasswordreset(null);
                m.setUnlockcode(null);
                m.setActivationcode(null);
                return m;
            } else {
                System.out.println("Login credentials provided were incorrect, password wrong.");
                return null;
            }
        } catch (Exception ex) {
            System.out.println("\nServer failed to login member:\n" + ex);
            return null;
        }
    }

    public String generatePasswordHash(String salt, String password) {
        String passwordHash = null;
        try {
            password = salt + password;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            passwordHash = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("\nServer failed to hash password.\n" + ex);
        }
        return passwordHash;
    }

    @GET
    @Path("account")
    @Produces("application/json")
    public MemberHelper getMemberAccount(@QueryParam("email") String email) {
        System.out.println("getMemberAccount is called");
        try {
            Query q = em.createQuery("SELECT m FROM Memberentity m where m.email=:email and m.isdeleted=FALSE");
            q.setParameter("email", email);
            Memberentity m = (Memberentity) q.getSingleResult();
            Loyaltytierentity loyalty = m.getLoyaltytierId();
            List<Itementity> wishList = m.getWishlistId().getItementityList();
            ArrayList<String> arrWishList = new ArrayList<>();
            for (Itementity i : wishList) {
                arrWishList.add(i.getSku() + ": " + i.getName());
            }
            MemberHelper helper = new MemberHelper();
            helper.setEmail(email);
            helper.setName(m.getName());
            helper.setTier(loyalty.getTier());
            helper.setPointsEarned(m.getLoyaltypoints());
            helper.setAmountSpent(m.getCummulativespending());
            helper.setWishList(arrWishList);
            System.out.println("Login credentials provided were incorrect, password wrong.");
            return helper;

        } catch (Exception ex) {
            System.out.println("\nServer failed to login member:\n" + ex);
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
