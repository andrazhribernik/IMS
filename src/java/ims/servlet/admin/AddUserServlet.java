/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet.admin;

import ims.entity.User;
import ims.management.LoginManagement;
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
@WebServlet(name = "AddUserServlet", urlPatterns = {"/AddUserServlet"})
public class AddUserServlet extends HttpServlet {

    private String getContent(String usernameMessage, String passwordMessage, String successMessage){
        String content="<div class=\"panel panel-default\"><div class=\"panel-heading\"><h3>Add User</h3></div>";
        content += "<form method=\"POST\" class=\"form-horizontal\" role=\"form\">";
        content += "<div class=\"form-group\">";
        content += "<label for=\"inputUsername\" class=\"col-sm-2 control-label\">Username</label>";
        content += "<div class=\"col-sm-10\">";

        content += "<input type=\"input\" class=\"form-control\" id=\"inputUsername\"  name=\"username\">";
        if(usernameMessage != null){
            content += "<div class=\"alert alert-danger\">"+usernameMessage+"</div>";
        }
        content += "</div></div>";

        content += "<div class=\"form-group\">";
        content += "<label for=\"inputPassword\" class=\"col-sm-2 control-label\">Password</label>";
        content += "<div class=\"col-sm-10\">";
        content += "<input type=\"input\" class=\"form-control\" id=\"inputPassword\"  name=\"password\">";
        if(passwordMessage != null){
            content += "<div class=\"alert alert-danger\">"+passwordMessage+"</div>";
        }
        content += "</div></div>";

        content += "<div class=\"form-group\">";
        content += "<label for=\"selectRole\" class=\"col-sm-2 control-label\">Role</label>";
        content += "<div class=\"col-sm-10\">";
        content += "<select class=\"form-control\" id=\"selectRole\" name=\"roleID\">";
        content +="<option value=\"1\">User</option>";
        content +="<option value=\"3\">Contributor</option>";

        content += "</select></div></div>";

        content += "<div class=\"form-group\">";
        content += "<div class=\"col-sm-offset-2 col-sm-10\">";
        content += "<button type=\"submit\" class=\"btn btn-default\">Add</button>";
        content += "</div></div>";
        content += "</form></div>";
        if(successMessage != null){
            content += "<div class=\"alert alert-success\">"+successMessage+"</div>";
        }
        return content;
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
        LoginManagement lm = new LoginManagement(request.getSession());
        lm.userPermissionForThisPage(response, new String[]{"admin"});
        
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String content = getContent(null, null,null);
            
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
        String password = request.getParameter("password");
        String roleId = request.getParameter("roleID");
        String username = request.getParameter("username");
        if(username == null){
            throw new ServletException("Parameter username is not set");
        }
        if(password == null){
            throw new ServletException("Parameter password is not set");
        }
        if(roleId == null){
            throw new ServletException("Parameter roleID is not set");
        }
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            if(username.length() < 4){
                String content = getContent("Username must contain at least 4 characters.", null,null);
                String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
                out.print(result);
                
            }
            else if(password.length() < 4){
                String content = getContent(null, "Password must contain at least 4 characters.",null);
                String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
                out.print(result);
                
            }
            else{
                UserManagement um = new UserManagement();
                try {
                    um.getUserByUsername(username);
                    String content = getContent("Username already exists..", null,null);
                    String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
                    out.print(result);

                } catch (Exception ex) {
                    um.addUser(username, password,new Integer(roleId));
                    String content = getContent(null, null,"You successfully added a new user.");
                    String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
                    out.print(result);

                }
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
