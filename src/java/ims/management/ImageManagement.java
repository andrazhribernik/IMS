/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Category;
import ims.entity.Image;
import ims.entity.Role;
import ims.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;
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
    
    /**
     * Method isImageNameEmpty return True if 'User u' has not uploaded image with
     * specified 'imageName'
     * 
     * @param String imageName
     * @param User u  
     * @return is 'imageName' empty for 'User u'
     */
    public boolean isImageNameEmpty(String imageName, User u){
        em.getEntityManagerFactory().getCache().evictAll();
        Vector<Image> images = (Vector<Image>) em.createNamedQuery("Image.findByNameAndUser").setParameter("name", imageName).setParameter("username", u.getUsername()).getResultList();
        if(images.size() == 0){
            return true;
        }
        return false;
    }
    
    /**
     * Add new 'Image i' to database
     * 
     * @param 'Image i'
     */
    public void addImage(Image i){
        em.getEntityManagerFactory().getCache().evictAll();
        Category category = (Category) em.createNamedQuery("Category.findByIdCategory").setParameter("idCategory", 1).getResultList().get(0);
        em.getTransaction().begin();
        try{
            i.setCategoryidCategory(category);
            em.persist(i);
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    
    /**
     * How many times 'Image i' has been sold
     * 
     * @param 'Image i'
     */
    public Integer soldHowManyTimes(Image i){
        em.getEntityManagerFactory().getCache().evictAll();
        return i.getUserSet().size();
    }
    
}
