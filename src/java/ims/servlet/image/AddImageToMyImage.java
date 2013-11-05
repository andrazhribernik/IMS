/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet.image;

import ims.entity.Image;
import ims.entity.User;
import ims.management.ImageManagement;
import ims.management.UserManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 
 * 
 * This servlet is made to link user with purchased image.
 * <p>
 * This servlet has the same behaviour for GET and POST request
 * @author andrazhribernik
 */
@WebServlet(name = "AddImageToMyImage", urlPatterns = {"/AddImageToMyImage"})
public class AddImageToMyImage extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * Process request requires two request parameters (imageId, password).
     * <p>
     * If the password is correct for particular image, then user gets access to
     * full-size image with that imageId. In that case user is redirected to their
     * image gallery.
     * <p>
     * If the password and imageId do not match, user is redirected to imageDetails
     * page with error message.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //request paremeter should have 2 parameters: image id and image size.
        String imageId = request.getParameter("imageId");
        Integer imageIdInt = 1;
        //if GET paramter imageId is not set, we throw an exception
        if(imageId == null){
            throw new ServletException("Parameter imageId is not set");
        }
        String userPassword = request.getParameter("imagePassword");
        if(userPassword == null){
            throw new ServletException("Parameter imagePassword is not set");
        } 
        
        try{
            imageIdInt = Integer.valueOf(imageId);
        }
        catch(Exception e){
            throw new ServletException("Wrong type parameter imageId.");
        }
        
        ImageManagement im = new ImageManagement();
        Image img = im.getImageById(imageId);
        if(!img.getPassword().equals(userPassword)){
            //throw new ServletException("Password parameter is wrong for selected image");
            response.sendRedirect("./imageDetails.jsp?pass=true&imageId="+imageId);
            return;
        }

        
        UserManagement um = new UserManagement();
        //at that moment we have only one user with username user1
        User currentUser = null;
        try {
            currentUser = um.getUserByUsername("user1");
        } catch (Exception ex) {
            Logger.getLogger(GetImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(currentUser == null){
            //User not exist
            throw new ServerException("Error.");
        }
        
        
        EntityManager em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
        em.getTransaction().begin();
        img.getUserSet().add(currentUser);
        em.merge(img);
        em.getTransaction().commit();
        em.close();
        System.out.println(currentUser.getImageSet().size());
        response.sendRedirect("./myImages.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
