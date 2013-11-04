/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Image;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author andrazhribernik
 */
@Stateless
public class ImageManagement2 {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
     //@PersistenceContext private EntityManager em;
    
    /*
     * Create class ImageManagement
     * 
     **/
    /*
    public List<Image> getAllImages(){
        em.getEntityManagerFactory().getCache().evictAll();
        List<Image> result = em.createNamedQuery("Image.findAll").getResultList();
        return result;
    }
    * */
}
