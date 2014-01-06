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
 *This class provides methods to dealing with sign in of users and getting information
 * of the currently signed in user.
 * @author andrazhribernik
 * 
 */
public class LoginManagement {
    private HttpSession session;
    private User user;
    private EntityManager em;
    
    /**
     * Constructor
     * @param HttpSession session 
     */
    public LoginManagement(HttpSession session){
        this.session = session;
        this.user = (User)session.getAttribute("user");
        em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
    }
    
    /**
     * Is user with current session logged in
     *  
     */
    public boolean isLoggedIn(){
        if(user == null)
            return false;
        return true;
    }
    
    /**
     * Sign up user with specified 'username' and 'password'
     * @param username
     * @param password
     * @return True if user with specified 'username' exists and 'username' and 'password' match
     *  
     */
    public boolean logIn(String username, String password){
        UserManagement um = new UserManagement();
        try {
            User u = um.getUserByUsername(username);
            // does passwod match with specified user
            if(u.getPassword().equals(password)){
                this.user = u;
                session.setAttribute("user", u);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception ex) {
            //user with specified username does not exist
            return false;
        }
    }
    /**
     * This method checks if currently logged in user is allowed to visit specified page.
     * If not, user is redirected to login page.
     * @param response
     * @param roles i.e. admin, user, etc. 
     */
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
    
    /**
     * Get user who is logged in current session
     * @return User
     */
    public User getUser() {
        if(user != null){
            em.getEntityManagerFactory().getCache().evictAll();
            user =  (User)em.createNamedQuery("User.findByIdUser").setParameter("idUser", user.getIdUser()).getResultList().get(0);
            session.setAttribute("user", user);
        }
        return user;
    }
    
}
