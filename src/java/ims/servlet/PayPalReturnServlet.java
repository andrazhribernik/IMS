/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet;

import ims.entity.Image;
import ims.management.ImageManagement;
import ims.management.LoginManagement;
import ims.management.PayPalAPIManeger;
import ims.management.TemplatingManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import sun.net.www.http.HttpClient;

/**
 *This servlet provides the password for purchased image.
 * <p>
 * This servlet has the same behaviour for GET and POST request.
 * 
 * 
 * @author andrazhribernik
 * 
 */
@WebServlet(name = "PayPalReturnServlet", urlPatterns = {"/conformationOfPurchase"})
public class PayPalReturnServlet extends HttpServlet {
    
    @PersistenceUnit
    //The emf corresponding to 
    private EntityManagerFactory emf;  
    
    @Resource
    private UserTransaction utx;
    
    /**
     * 
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     *<p> This servlet return html page. If request parameters are valid, user 
     * gets a password for accessing purchased image, otherwise user get message 
     * "Wrong parameters"
     * 
     * <p>
     * Obligatory request parameters are:
     * <ul> 
     * <li>tx-paypal transaction id</li>
     * <li>item_number-image number</li>
     * </ul>
     * 
     * <p>First the method checks if parameters are set. After that method connects
     * to PayPal API and check if particular transaction exists and if item_number is 
     * the same as image number, that user is looking for. If everything is valid 
     * html page includes password for selected image.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //PayPal return request has two parameters: transaction id and item_id
        String transactionId = request.getParameter("tx");
        String itemId = request.getParameter("item_number");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if(transactionId == null || itemId == null){
                String message = "<div class=\"alert alert-danger\">Wrong parameters</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message, request.getSession()));
                return;
            }
            
            ImageManagement im = new ImageManagement();
            Image currentImage = im.getImageById(itemId);
            if(currentImage == null){
                String message = "<div class=\"alert alert-danger\">Wrong parameters</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message, request.getSession()));
                return;
            }
            
            PayPalAPIManeger ppam = new PayPalAPIManeger();
            if(!ppam.checkTransactionWithItem(transactionId, String.valueOf(currentImage.getIdImage()), currentImage.getPriceD())){
                String message = "<div class=\"alert alert-danger\">Wrong parameters</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message, request.getSession()));
                return;
            }
            
            LoginManagement lm = new LoginManagement(request.getSession());
            if(!lm.isLoggedIn()){
                String message = "<div class=\"alert alert-danger\">You have to be logged in.</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message, request.getSession()));
                return;
            }
            
            
            EntityManager em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
            em.getTransaction().begin();
            currentImage.getUserSet().add(lm.getUser());
            em.merge(currentImage);
            em.getTransaction().commit();
            em.close();

            
            
            
            String message = "";
            message += "<p>Thank you for your purchase.</p>";
            message += "<a href=\"myImages.jsp\">List purchased images.</a>";
            
            out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message, request.getSession()));
            
        }
        catch(Exception e){
            e.printStackTrace();
        }  
        finally {            
            out.close();
        }
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
