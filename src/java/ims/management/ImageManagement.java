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
 *This class provides methods to accessing images into database.
 * @author andrazhribernik
 */

public class ImageManagement {
    

    
    private EntityManager em;

    public ImageManagement(){
        em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
    }
    
    /**
     * Method getAllImages return all images that are stored into database.
     * 
     * @return List of objects <code>ims.entity.Image</code>
     */
    public List<Image> getAllImages(){
        em.getEntityManagerFactory().getCache().evictAll();
        List<Image> result = em.createNamedQuery("Image.findAll").getResultList();
        return result;
    }
    
    /**
     * Method getImageById return the image that has the same id as parameter idImage.
     * 
     * @param idImage is string with number which is the same as id of image that user is looking for 
     * @return Image Object Image for specified idImage. If image with specified id does
     * not exist, return value is null
     */
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
