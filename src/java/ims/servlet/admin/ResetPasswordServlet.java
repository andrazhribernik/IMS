/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet.admin;

import ims.entity.LostPasswordRequest;
import ims.entity.User;
import ims.management.LoginManagement;
import ims.management.ResetPasswordManagement;
import ims.management.TemplatingManagement;
import ims.management.UserManagement;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author andrazhribernik
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPassword"})
public class ResetPasswordServlet extends HttpServlet {


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
        LoginManagement lm = new LoginManagement(request.getSession());
        lm.userPermissionForThisPage(response, new String[]{"admin"});
        
        String requestId = request.getParameter("requestID");
        if(requestId == null){
            throw new ServletException("Parameter requestID is not set");
        }
        
        try{
            Integer.valueOf(requestId);
        }
        catch(Exception e){
            throw new ServletException("Parameter requestID is not integer.");
        }
        
        ResetPasswordManagement rpm = new ResetPasswordManagement();
        LostPasswordRequest lpr = rpm.getResetRequestById(Integer.valueOf(requestId));
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String content="<div class=\"panel panel-default\"><div class=\"panel-heading\">Reset Password</div>";
            content += "<form method=\"POST\" class=\"form-horizontal\" role=\"form\">";
            content += "<div class=\"form-group\">";
            content += "<label class=\"col-sm-2 control-label\">Username</label>";
            content += "<div class=\"col-sm-10\">";
            content += "<p class=\"form-control-static\">"+lpr.getUseridUser().getUsername()+"</p>";
            content += "</div></div>";
            
            content += "<div class=\"form-group\">";
            content += "<label for=\"inputPassword\" class=\"col-sm-2 control-label\">Password</label>";
            content += "<div class=\"col-sm-10\">";
            content += "<input type=\"input\" class=\"form-control\" id=\"inputPassword\" value=\""+lpr.getUseridUser().getPassword()+"\" name=\"password\">";
            content += "</div></div>";
            
            
            
            content += "<div class=\"form-group\">";
            content += "<div class=\"col-sm-offset-2 col-sm-10\">";
            content += "<button type=\"submit\" class=\"btn btn-default\">Reset password</button>";
            content += "</div></div>";
            content += "<input type=\"hidden\" value=\""+lpr.getIdLostPasswordRequest()+"\" name=\"requestID\" />";
            content += "</form>";
            
            String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
            out.print(result);
        } finally {            
            out.close();
        }
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
        LoginManagement lm = new LoginManagement(request.getSession());
        lm.userPermissionForThisPage(response, new String[]{"admin"});
        
        String requestId = request.getParameter("requestID");
        String password = request.getParameter("password");
        if(requestId == null){
            throw new ServletException("Parameter requestID is not set");
        }
        if(password == null){
            throw new ServletException("Parameter password is not set");
        }

        try{
            Integer.valueOf(requestId);
        }
        catch(Exception e){
            throw new ServletException("Parameter requestID is not integer.");
        }
        
        ResetPasswordManagement rpm = new ResetPasswordManagement();
        LostPasswordRequest lpr = rpm.getResetRequestById(Integer.valueOf(requestId));
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            if(password.length() < 4){
                String content="<div class=\"panel panel-default\"><div class=\"panel-heading\">Reset Password</div>";
                content += "<form method=\"POST\" class=\"form-horizontal\" role=\"form\">";
                content += "<div class=\"form-group\">";
                content += "<label class=\"col-sm-2 control-label\">Username</label>";
                content += "<div class=\"col-sm-10\">";
                content += "<p class=\"form-control-static\">"+lpr.getUseridUser().getUsername()+"</p>";
                content += "</div></div>";

                content += "<div class=\"form-group\">";
                content += "<label for=\"inputPassword\" class=\"col-sm-2 control-label\">Password</label>";
                content += "<div class=\"col-sm-10\">";
                content += "<input type=\"input\" class=\"form-control\" id=\"inputPassword\" value=\""+lpr.getUseridUser().getPassword()+"\" name=\"password\">";
                content += "<div class=\"alert alert-danger\">Password field must contain at least 4 chatacters.</div>";
                content += "</div></div>";



                content += "<div class=\"form-group\">";
                content += "<div class=\"col-sm-offset-2 col-sm-10\">";
                content += "<button type=\"submit\" class=\"btn btn-default\">Reset password</button>";
                content += "</div></div>";
                content += "<input type=\"hidden\" value=\""+lpr.getIdLostPasswordRequest()+"\" name=\"requestID\" />";
                content += "</form>";

                String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
                out.print(result);
            }
            else{
                lpr.getUseridUser().setPassword(password);
                rpm.finishResetRequest(lpr,request.getServerName(),String.valueOf(request.getServerPort()));
                response.sendRedirect("./ResetPasswordRequestsList");
            }
        } finally {            
            out.close();
        }
        
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
