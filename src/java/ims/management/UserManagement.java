/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Image;
import ims.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author andrazhribernik
 */
@Stateless
public class UserManagement {
   
    /*
     * Create class ImageManagement
     * 
     **/
    public UserManagement(){
        //System.out.println(emf);
        
    }
    
    public static User getUserByUsername(String username) throws Exception{
        EntityManager em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
        em.getTransaction().begin();
        List<User> images = (List<User>) em.createNamedQuery("User.findByUsername").setParameter("username", username).getResultList();
        em.getTransaction().commit();
        em.close();
        if(images.size() == 0){
            throw new Exception("User with that particular username does not exist.");
        }
        return images.get(0);
        
    }
    
    public static Set<Image> getUserImages(String username) throws Exception{
        User user = getUserByUsername(username);
        return user.getImageSet();
    }
    
}
