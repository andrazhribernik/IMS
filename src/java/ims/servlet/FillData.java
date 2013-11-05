/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.servlet;

import ims.entity.Category;
import ims.entity.Image;
import ims.entity.Role;
import ims.entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

/**
 * This class is used only for development purposes to fulfill a database.
 * @author andrazhribernik
 *
 */
@WebServlet(name = "FillData", urlPatterns = {"/FillData"})
public class FillData extends HttpServlet {
    
    
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
        
              
        
        EntityManager em = Persistence.createEntityManagerFactory("web-jpaPU").createEntityManager();
        //persist the person entity
        
        em.getTransaction().begin();
        try{
            Role r = new Role();
            r.setName("user");
            em.persist(r);

            r = new Role();
            r.setName("admin");
            em.persist(r);

            Category c = new Category();
            c.setCategoryName("nature");
            em.persist(c);


            User u = new User();
            u.setRoleidRole(r);
            u.setUsername("admin");
            u.setPassword("admin");
            em.persist(u);

            for(int i=1; i<=10; i++){
                Image img = new Image();
                img.setCategoryidCategory(c);
                img.setDate(new Date());
                img.setName("image"+String.valueOf(i)+".jpg");
                img.setUseridUser(u);
                img.setPassword("password"+String.valueOf(i));

                em.persist(img);
            }
            em.getTransaction().commit();
        }
        catch(Exception e){
            em.getTransaction().rollback();
        }
        finally {
            em.close();
        }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FillData</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Successfully ful database.</h1>");
            out.println("</body>");
            out.println("</html>");
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
