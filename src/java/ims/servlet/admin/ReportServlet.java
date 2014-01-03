/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet.admin;

import ims.entity.Image;
import ims.entity.User;
import ims.management.Constants;
import ims.management.ImageManagement;
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
@WebServlet(name = "ReportServlet", urlPatterns = {"/ReportServlet"})
public class ReportServlet extends HttpServlet {

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
        LoginManagement lm = new LoginManagement(request.getSession());
        lm.userPermissionForThisPage(response, new String[]{"admin"});
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            String content = 
                    "<div class=\"panel panel-default\"><div class=\"panel-heading\">"
                    + "<h3>Images</h3>"
                    + "</div>"
                    + "<div class=\"panel-body\">"
                    + "<table class=\"table table-striped\">";
            content +="<thead><tr><th>Image</th><th>Items sold</th><th>Income</th></tr></thead>";
            content += "<tbody>";
            ImageManagement um = new ImageManagement();
            Double income = 0.0;
            for(Image i:um.getAllImages()){
                content+="<tr><td>"+i.getName()+"</td>";
                content+="<td>"+i.getUserSet().size()+"</td>";
                content+="<td>&euro; "+i.getUserSet().size() * i.getPriceD()+"</td>";
                income += i.getUserSet().size() * i.getPriceD();
            }
            String incomeMessage = "<dl><dt>Income</dt><dd>&euro; "+income+"</dd>"
                    +"<dt>Profit</dt><dd>&euro; "+income * Constants.providerPercent +"</dd>"
                    + "</dl>";
            content+="</tbody></table></div>"
                    + "<div class=\"panel-footer\">"+incomeMessage+"</div>"
                    + "</div>";
            
            
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
