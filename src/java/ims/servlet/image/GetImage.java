/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet.image;

import ims.entity.Image;
import ims.entity.User;
import ims.management.ImageManagement;
import ims.management.UserManagement;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author andrazhribernik
 */
@WebServlet(name = "GetImage", urlPatterns = {"/GetImage"})
public class GetImage extends HttpServlet {
    
    @PersistenceUnit
    //The emf corresponding to 
    private EntityManagerFactory emf;  
    
    @Resource
    private UserTransaction utx;
    
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
    
    public Image getImageFromId(int imageId) throws ServletException{
        
        Image img;
        //assert emf != null;  //Make sure injection went through correctly.
        EntityManager em = null;

        try {            
            //begin a transaction
            //utx.begin();
            //create an em. 
            //Since the em is created inside a transaction, it is associsated with 
            //the transaction
            em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
            ArrayList<Image> images = (ArrayList<Image>) em.createNamedQuery("Image.findByIdImage").setParameter("idImage", imageId).getResultList();
            if(images.size() == 0){
                throw new ServletException("Invalid imageId value.");
            }
            img = images.get(0);
            
            //commit transaction which will trigger the em to 
            //commit newly created entity into database
            //utx.commit();
            
            //Forward to ListPerson servlet to list persons along with the newly
            //created person above
            //request.getRequestDispatcher("ListPerson").forward(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            //close the em to release any resources held up by the persistebce provider
            if(em != null) {
                em.close();
            }
        }
        return img;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //request paremeter should have 2 parameters: image id and image size.
        String imageId = request.getParameter("imageId");
        Integer imageIdInt = 1;
        //if GET paramter imageId is not set, we throw an exception
        if(imageId == null){
            throw new ServletException("Parameter imageId is not set");
        }
        try{
            imageIdInt = Integer.valueOf(imageId);
        }
        catch(Exception e){
            throw new ServletException("Wrong type parameter imageId.");
        }
        
        Image img = this.getImageFromId(imageIdInt);
        
        
        //Source code for returning image is from http://stackoverflow.com/questions/2979758/writing-image-to-servlet-response-with-best-performance
        
        
        String imageSize = request.getParameter("size");
        if(imageSize == null){
            imageSize = "300";
        }
        else if(!(imageSize.equals("600") || imageSize.equals("300"))){
            imageSize = "300";
        }
        
        
        ServletContext cntx= getServletContext();
        // Get the absolute path of the image
        //image path is set regarding imageId and imageSize
        String imagePath = "Images/"+imageSize+"/"+img.getName();
        System.out.println(imagePath);
        String filename = cntx.getRealPath(imagePath);
        String mime = cntx.getMimeType(filename);
        if (mime == null) {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          return;
        }

        response.setContentType(mime);
        File file = new File(filename);
        response.setContentLength((int)file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        // Copy the contents of the file to the output stream
         byte[] buf = new byte[1024];
         int count = 0;
         while ((count = in.read(buf)) >= 0) {
           out.write(buf, 0, count);
        }
        out.close();
        in.close();
        
        
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
        
        Image img = this.getImageFromId(imageIdInt);
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
        
        ImageManagement im = new ImageManagement();
        Image boughtImage = im.getImageById(imageId);
        
        EntityManager em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
        em.getTransaction().begin();
        boughtImage.getUserSet().add(currentUser);
        em.merge(boughtImage);
        em.getTransaction().commit();
        em.close();
        System.out.println(currentUser.getImageSet().size());
        response.sendRedirect("./myImages.jsp");
        return;
        
        /*       
        //Source code for returning image is from http://stackoverflow.com/questions/2979758/writing-image-to-servlet-response-with-best-performance
        
        
        
        ServletContext cntx= getServletContext();
        // Get the absolute path of the image
        //image path is set regarding imageId and imageSize
        String imagePath = "Images/original/"+img.getName();
        System.out.println(imagePath);
        String filename = cntx.getRealPath(imagePath);
        String mime = cntx.getMimeType(filename);
        if (mime == null) {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          return;
        }

        //response.setContentType(mime);
        response.setContentType("binary/octet-stream");
        File file = new File(filename);
        response.setContentLength((int)file.length());

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        // Copy the contents of the file to the output stream
         byte[] buf = new byte[1024];
         int count = 0;
         while ((count = in.read(buf)) >= 0) {
           out.write(buf, 0, count);
        }
        out.close();
        in.close();
        */
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
