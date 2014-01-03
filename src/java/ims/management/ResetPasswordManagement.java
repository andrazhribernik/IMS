/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Image;
import ims.entity.LostPasswordRequest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author andrazhribernik
 */
public class ResetPasswordManagement {
    private EntityManager em;
    
    public ResetPasswordManagement(){
        em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
    }
    
    public void addResetPasswordRequest(LostPasswordRequest lpr){
        em.getEntityManagerFactory().getCache().evictAll();
        em.getTransaction().begin();
        try{
            
            em.persist(lpr);
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    
    public void finishResetRequest(LostPasswordRequest lpr){
        em.getTransaction().begin();
        try{
            lpr.setIsRead(Boolean.TRUE);
            em.persist(lpr);
            em.getTransaction().commit();
            SendMailTLS mailManagement = new SendMailTLS();
            String mailContent = "Dean "+lpr.getUseridUser().getUsername()+"<br><br>";
            mailContent += "new password is: <strong>"+lpr.getUseridUser().getPassword()+"</strong><br>";
            mailContent += "You can log in <a href=\"http://192.168.3.102:8080/IMS_CS3510/logIn.jsp\">here</>.";
            mailManagement.sendEmail(lpr.getEmail(), mailContent);
        }
        catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    
    public List<LostPasswordRequest> getAllRequests(){
        em.getEntityManagerFactory().getCache().evictAll();
        List<LostPasswordRequest> result = em.createNamedQuery("LostPasswordRequest.findAll").getResultList();
        return result;
    }
    
    public LostPasswordRequest getResetRequestById(Integer id){
        em.getEntityManagerFactory().getCache().evictAll();
        LostPasswordRequest result = (LostPasswordRequest) em.createNamedQuery("LostPasswordRequest.findByIdLostPasswordRequest").setParameter("idLostPasswordRequest", id).getResultList().get(0);
        return result;
    }
}
