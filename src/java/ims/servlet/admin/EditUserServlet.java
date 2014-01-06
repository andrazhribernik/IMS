/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet.admin;

import ims.entity.Role;
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
@WebServlet(name = "EditUser", urlPatterns = {"/EditUser"})
public class EditUserServlet extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     * Show html form for editing selected user.
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
        
        String userId = request.getParameter("userID");
        if(userId == null){
            throw new ServletException("Parameter userID is not set");
        }
        
        try{
            Integer.valueOf(userId);
        }
        catch(Exception e){
            throw new ServletException("Parameter userID is not integer.");
        }
        
        UserManagement um = new UserManagement();
        User user = um.getUserById(Integer.valueOf(userId));
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String content="<div class=\"panel panel-default\"><div class=\"panel-heading\">Edit User</div>";
            content += "<form method=\"POST\" class=\"form-horizontal\" role=\"form\">";
            content += "<div class=\"form-group\">";
            content += "<label class=\"col-sm-2 control-label\">Username</label>";
            content += "<div class=\"col-sm-10\">";
            content += "<p class=\"form-control-static\">"+user.getUsername()+"</p>";
            content += "</div></div>";
            
            content += "<div class=\"form-group\">";
            content += "<label for=\"inputPassword\" class=\"col-sm-2 control-label\">Password</label>";
            content += "<div class=\"col-sm-10\">";
            content += "<input type=\"input\" class=\"form-control\" id=\"inputPassword\" value=\""+user.getPassword()+"\" name=\"password\">";
            content += "</div></div>";
            
            content += "<div class=\"form-group\">";
            content += "<label for=\"selectRole\" class=\"col-sm-2 control-label\">Role</label>";
            content += "<div class=\"col-sm-10\">";
            content += "<select class=\"form-control\" id=\"selectRole\" name=\"roleID\">";
            if(user.getRoleidRole().getName().equals("user")){
                content +="<option value=\"1\">User</option>";
                content +="<option value=\"3\">Contributor</option>";
            }
            else{
                content +="<option value=\"3\">Contributor</option>";
                content +="<option value=\"1\">User</option>";
            }
            content += "</select></div></div>";
            
            content += "<div class=\"form-group\">";
            content += "<div class=\"col-sm-offset-2 col-sm-10\">";
            content += "<button type=\"submit\" class=\"btn btn-default\">Edit</button>";
            content += "</div></div>";
            content += "<input type=\"hidden\" value=\""+user.getIdUser()+"\" name=\"user\" />";
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
     *Handles admin request for editing selected user.
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
        
        String userId = request.getParameter("user");
        String password = request.getParameter("password");
        String roleId = request.getParameter("roleID");
        if(userId == null){
            throw new ServletException("Parameter user is not set");
        }
        if(password == null){
            throw new ServletException("Parameter password is not set");
        }
        if(roleId == null){
            throw new ServletException("Parameter roleID is not set");
        }
        
        UserManagement um = new UserManagement();
        User user = um.getUserById(Integer.valueOf(userId));
        
        //if password is to short inform admin about error.
        if(password.length() < 4){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                String content="<div class=\"panel panel-default\"><div class=\"panel-heading\">Edit User</div>";
                content += "<form method=\"POST\" class=\"form-horizontal\" role=\"form\">";
                content += "<div class=\"form-group\">";
                content += "<label class=\"col-sm-2 control-label\">Username</label>";
                content += "<div class=\"col-sm-10\">";
                content += "<p class=\"form-control-static\">"+user.getUsername()+"</p>";
                content += "</div></div>";

                content += "<div class=\"form-group\">";
                content += "<label for=\"inputPassword\" class=\"col-sm-2 control-label\">Password</label>";
                content += "<div class=\"col-sm-10\">";
                content += "<input type=\"input\" class=\"form-control\" id=\"inputPassword\" value=\""+user.getPassword()+"\" name=\"password\">";
                content += "</div></div>";
                content += "<div class=\"alert alert-danger\">Password field should be at least 4 characters long.</div>";

                content += "<div class=\"form-group\">";
                content += "<label for=\"selectRole\" class=\"col-sm-2 control-label\">Role</label>";
                content += "<div class=\"col-sm-10\">";
                content += "<select class=\"form-control\" id=\"selectRole\" name=\"roleID\">";
                if(user.getRoleidRole().getName().equals("user")){
                    content +="<option value=\"1\">User</option>";
                    content +="<option value=\"3\">Contributor</option>";
                }
                else{
                    content +="<option value=\"3\">Contributor</option>";
                    content +="<option value=\"1\">User</option>";
                }
                content += "</select></div></div>";

                content += "<div class=\"form-group\">";
                content += "<div class=\"col-sm-offset-2 col-sm-10\">";
                content += "<button type=\"submit\" class=\"btn btn-default\">Edit</button>";
                content += "</div></div>";
                content += "<input type=\"hidden\" value=\""+user.getIdUser()+"\" name=\"user\" />";
                content += "</form>";

                
                String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content, request.getSession());
                out.print(result);
            } finally {            
                out.close();
            }
        }
        else{
            //edit selected user
            user.setPassword(password);
            user.setRoleidRole(um.getRoleById(Integer.valueOf(roleId)));
            um.editUser(user);
            response.sendRedirect("./UserManagementServlet");
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
