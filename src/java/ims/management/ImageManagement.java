/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Image;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author andrazhribernik
 */

public class ImageManagement {
    

    
    /*
     * Create class ImageManagement
     * 
     **/
    private EntityManager em;
    
    public ImageManagement(){
        em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
    }
    
    public List<Image> getAllImages(){
        em.getEntityManagerFactory().getCache().evictAll();
        List<Image> result = em.createNamedQuery("Image.findAll").getResultList();
        return result;
    }
    
    
    public Image getImageById(String idImage){
        Integer imageId=0;
        try{
            imageId = Integer.valueOf(idImage);
        }
        catch(Exception e){
            return null;
        }
        em.getEntityManagerFactory().getCache().evictAll();
        ArrayList<Image> images = (ArrayList<Image>) em.createNamedQuery("Image.findByIdImage").setParameter("idImage", imageId).getResultList();
        if(images.size() == 0){
            return null;
        }
        return images.get(0);
    }
    
}
