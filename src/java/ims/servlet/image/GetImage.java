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
 *This servlet provides image with specified imageId.
 * <p>
 * Get method returns user free accessible images (sizes 300px&600px)
 * <p>
 * Post method returns user full size image (1200px)
 * 
 * @author andrazhribernik
 */
@WebServlet(name = "GetImage", urlPatterns = {"/GetImage"})
public class GetImage extends HttpServlet {
   
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *<p>
     * This method return image regarding request parameters. If request parameter imageId is not set
     * or the value does not exist, method returns ServletException. Size of image depends on
     * request parameter size.
     * <p>
     * Mime type is set to Image/"imagename". This means that browser shows image
     * in browser.
     * <p>
     * Obligatory request parameter is:
     * <ul> 
     * <li>imageId-id of image that user wants</li>
     * </ul>
     *  <p>
     * Non-Obligatory request parameter is:
     * <ul> 
     * <li>size-image size of returned image. Default value is 300px. If you set value 
     * size=600 you get image with 600px width, otherwise 300px.</li>
     * </ul>
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //request paremeter should have 2 parameters: image id and image size.
        String imageId = request.getParameter("imageId");
        //if GET paramter imageId is not set, we throw an exception
        if(imageId == null){
            throw new ServletException("Parameter imageId is not set");
        }
                
        ImageManagement im = new ImageManagement();
        Image img = im.getImageById(imageId);
        if(img == null){
            throw new ServletException("Wrong type parameter imageId.");
        }
        
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
        String imagePath = "Images/"+imageSize+"/"+img.getUseridUser().getUsername()+"/"+img.getName();
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
     * <p>
     * This method return image regarding request parameters. If request parameter imageId is not set
     * or the value does not exist, method returns ServletException. Size of image is 1200px width.
     * <p>
     * Content type is set to application//octet-stream. This means that image is downloaded
     * to user.
     * 
     * <p>
     * If the image, that user would like to access, is not on the user's image list,
     * user is redirected to image details and image is not downloaded.
     * <p>
     * Obligatory request parameter is:
     * <ul> 
     * <li>imageId-id of image that user wants</li>
     * </ul>
     *  <p>
     *
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String imageId = request.getParameter("imageId");
        Integer imageIdInt = 1;
        //if GET paramter imageId is not set, we throw an exception
        if(imageId == null){
            throw new ServletException("Parameter imageId is not set");
        }
        
        ImageManagement im = new ImageManagement();
        Image img = im.getImageById(imageId);
        
        if(img == null){
            throw new ServletException("Wrong type parameter imageId.");
        }
        
        //At that moment default user is user with userId = 2
        //when we will implemet log in we will get user from session, othervise we will throw error.
            
        UserManagement um = new UserManagement();
        User curUser = um.getUserById(2);
        
        if(!curUser.getImageSet().contains(img)){
            response.sendRedirect("./imageDetails.jsp?pass=true&imageId="+imageId);
            return;
        }
        
        //Source code for returning image is from http://stackoverflow.com/questions/2979758/writing-image-to-servlet-response-with-best-performance
        
        
        
        ServletContext cntx= getServletContext();
        // Get the absolute path of the image
        //image path is set regarding imageId and imageSize
        String imagePath = "Images/1200/"+img.getUseridUser().getUsername()+"/"+img.getName();
        System.out.println(imagePath);
        String filename = cntx.getRealPath(imagePath);
        String mime = cntx.getMimeType(filename);
        if (mime == null) {
          response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
          return;
        }

        //response.setContentType(mime);
        response.setContentType("application/octet-stream");
        File file = new File(filename);
        response.setContentLength((int)file.length());

        // sets HTTP header
        response.setHeader("Content-Disposition", "attachment; filename=\"" + img.getName() + "\"");
        
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
