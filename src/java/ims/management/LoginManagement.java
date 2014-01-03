/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Image;
import ims.entity.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpRequest;

/**
 *
 * @author andrazhribernik
 */
public class LoginManagement {
    private HttpSession session;
    private User user;
    private EntityManager em;
    
    public LoginManagement(HttpSession session){
        this.session = session;
        this.user = (User)session.getAttribute("user");
        em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
    }
    public boolean isLoggedIn(){
        if(user == null)
            return false;
        return true;
    }
    
    public boolean logIn(String username, String password){
        UserManagement um = new UserManagement();
        try {
            User u = um.getUserByUsername(username);
            if(u.getPassword().equals(password)){
                this.user = u;
                session.setAttribute("user", u);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public void userPermissionForThisPage(HttpServletResponse response, String [] roles){
        if(user == null){
            try {
                response.sendRedirect("./logIn.jsp");
            } catch (IOException ex) {
                Logger.getLogger(LoginManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        boolean isAllowed = false;
        for(String role:roles){
            if(role.equals(user.getRoleidRole().getName())){
                isAllowed = true;
                break;
            }
        }
        if(!isAllowed){
            try {
                response.sendRedirect("./logIn.jsp");
            } catch (IOException ex) {
                Logger.getLogger(LoginManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public User getUser() {
        if(user != null){
            em.getEntityManagerFactory().getCache().evictAll();
            user =  (User)em.createNamedQuery("User.findByIdUser").setParameter("idUser", user.getIdUser()).getResultList().get(0);
        }
        return user;
    }
    
}
