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
 *This sevlet shows a list of all users with their data.
 * @author andrazhribernik
 */
@WebServlet(name = "UserManagementServlet", urlPatterns = {"/UserManagementServlet"})
public class UserManagementServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *Shows html table with users.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginManagement lm = new LoginManagement(request.getSession());
        lm.userPermissionForThisPage(response, new String[]{"admin"});
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            String content = "<a href=\"AddUserServlet\" >Add User</a>" 
                    +"<div class=\"panel panel-default\"><div class=\"panel-heading\">"
                    + "<h3>Users</h3>"
                    + "</div><table class=\"table table-striped\">";
            content +="<thead><tr><th>username</th><th>password</th><th>Role</th><th></th></tr></thead>";
            content += "<tbody>";
            UserManagement um = new UserManagement();
            for(User u:um.getAllUsersExceptAdmin()){
                content+="<tr><td>"+u.getUsername()+"</td>";
                content+="<td>"+u.getPassword()+"</td>";
                content+="<td>"+u.getRoleidRole().getName()+"</td>";
                content+="<td><a href=\"EditUser?userID="+u.getIdUser()+"\">Edit</a></td></tr>";
            }
            content+="</tbody></table></div></div>";

            String result = TemplatingManagement.getTemplateWithContent(this.getServletContext(),content , request.getSession());
            out.print(result);
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
