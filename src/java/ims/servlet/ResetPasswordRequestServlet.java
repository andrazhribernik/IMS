/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet;

import ims.entity.LostPasswordRequest;
import ims.entity.User;
import ims.management.LoginManagement;
import ims.management.ResetPasswordManagement;
import ims.management.UserManagement;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andrazhribernik
 */
@WebServlet(name = "ResetPasswordRequestServlet", urlPatterns = {"/ResetPasswordRequest"})
public class ResetPasswordRequestServlet extends HttpServlet {
    
    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
     }

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
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        if(username == null){
            throw new ServerException("Parameter username is not set");
        }
        if(email == null){
            throw new ServerException("Parameter email is not set");
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            UserManagement um = new UserManagement();
            User user = um.getUserByUsername(username);
            
            if(!isValidEmailAddress(email)){
                response.sendRedirect("./resetPasswordForm.jsp?messageEmail=Invalid email address.");
                return;
            }
            
            LostPasswordRequest lpr = new LostPasswordRequest();
            lpr.setEmail(email);
            lpr.setUseridUser(user);
            lpr.setIsRead(Boolean.FALSE);
            
            ResetPasswordManagement rpm = new ResetPasswordManagement();
            rpm.addResetPasswordRequest(lpr);
            response.sendRedirect("./resetPasswordForm.jsp?message=Your request has been sent to administrator. We will respond to your email as soon as possible.");
            
        } catch (Exception ex) {
            response.sendRedirect("./resetPasswordForm.jsp?messageUsername=This username does not exist.");
        } finally {            
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
