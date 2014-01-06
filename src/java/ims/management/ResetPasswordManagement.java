package ims.management;

import ims.entity.Image;
import ims.entity.LostPasswordRequest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *This class provides methods to dealing with users' requests for reset of their passwords. 
 * @author andrazhribernik
 */
public class ResetPasswordManagement {
    private EntityManager em;
    
    public ResetPasswordManagement(){
        em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
    }
    
    /**
     * Add lostPasswordRequest to database.
     * @param lostPasswordRequest 
     */
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
    
    /**
     * This method change 'lostPasswordRequest' property isRead to True and send an email to user with new password. 
     * @param lpr selected lost password request
     * @param host server host name
     * @param port server port number
     */
    public void finishResetRequest(LostPasswordRequest lpr,String host, String port){
        em.getTransaction().begin();
        try{
            lpr.setIsRead(Boolean.TRUE);
            em.persist(lpr);
            em.getTransaction().commit();
            SendMailTLS mailManagement = new SendMailTLS();
            String mailContent = "Dean "+lpr.getUseridUser().getUsername()+"<br><br>";
            mailContent += "new password is: <strong>"+lpr.getUseridUser().getPassword()+"</strong><br>";
            mailContent += "You can log in <a href=\"http://"+host+":"+port+"/IMS_CS3510/logIn.jsp\">here</>.";
            mailManagement.sendEmail(lpr.getUseridUser().getUsername(), mailContent);
        }
        catch(Exception e){
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }
    /**
     * This method return list of all users' request for reset of passwords.
     * @return List of all users' request for reset of passwords
     */
    public List<LostPasswordRequest> getAllRequests(){
        em.getEntityManagerFactory().getCache().evictAll();
        List<LostPasswordRequest> result = em.createNamedQuery("LostPasswordRequest.findAll").getResultList();
        return result;
    }
    
    /**
     * This method return entity LostPasswordRequest which has 'id' as is specified as parameter.
     * @param id
     * @return LostPasswordRequest with specified 'id'
     */
    public LostPasswordRequest getResetRequestById(Integer id){
        em.getEntityManagerFactory().getCache().evictAll();
        LostPasswordRequest result = (LostPasswordRequest) em.createNamedQuery("LostPasswordRequest.findByIdLostPasswordRequest").setParameter("idLostPasswordRequest", id).getResultList().get(0);
        return result;
    }
}
