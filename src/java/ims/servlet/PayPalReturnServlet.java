/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet;

import ims.entity.Image;
import ims.management.ImageManagement;
import ims.management.PayPalAPIManeger;
import ims.management.TemplatingManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.net.www.http.HttpClient;

/**
 *
 * @author andrazhribernik
 */
@WebServlet(name = "PayPalReturnServlet", urlPatterns = {"/conformationOfPurchase"})
public class PayPalReturnServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String transactionId = request.getParameter("tx");
        String itemId = request.getParameter("item_number");
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if(transactionId == null || itemId == null){
                String message = "<div class=\"alert alert-danger\">Wrong parameters</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message));
                return;
            }
            
            ImageManagement im = new ImageManagement();
            Image currentImage = im.getImageById(itemId);
            if(currentImage == null){
                String message = "<div class=\"alert alert-danger\">Wrong parameters</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message));
                return;
            }
            
            PayPalAPIManeger ppam = new PayPalAPIManeger();
            if(!ppam.checkTransactionWithItem(transactionId, String.valueOf(currentImage.getIdImage()))){
                String message = "<div class=\"alert alert-danger\">Wrong parameters</div>";
                out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message));
                return;
            }
            
            String message = "";
            message += "<p>Your password for purchased image is <strong>";
            message += currentImage.getPassword();
            message += ".</strong></p>";
            message += "<a href=\"imageDetails.jsp?imageId="+currentImage.getIdImage()+"\">Back to image</a>";
            
            out.write(TemplatingManagement.getTemplateWithContent(this.getServletContext(), message));
            
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
