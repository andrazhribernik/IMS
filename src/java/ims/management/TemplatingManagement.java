/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import ims.entity.Role;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *This class provides methods to use <code>html</code> template with own content.
 * Those methods are particularly useful when we have to return html page as 
 * servlet response.
 * @author andrazhribernik
 */
public class TemplatingManagement {
    
    /**
     * This method generates String which include html page. This page has the 
     * same layout as other jsp pages in that project. User can define content 
     * of that page with parameter content.
     * 
     * <p>This method can be called only from Servlet.
     * 
     * @param context Servlet context is needed for providing path to file which 
     * include template html.
     * @param content This content will be shown in content area of web page.
     * @return This method return String which include html page with common
     * layout and content which was specified as parameter.
     * @throws IOException 
     */
    public static String getTemplateWithContent(ServletContext context,String content, HttpSession session) throws IOException{

        String path = context.getRealPath("/templates/index.txt");
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String template = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
        template = template.replace("<!--TemplateContent-->", content);
        template = populateNavbar(template, session, context);
        
        return template;

    }
    
    public static String populateNavbar(String template, HttpSession session, ServletContext context) throws IOException{
        LoginManagement lm = new LoginManagement(session);
        if(lm.isLoggedIn()){
            String uRoleName = lm.getUser().getRoleidRole().getName();
            if(uRoleName.equals("user")){
                String path = context.getRealPath("/templates/userNavigationBar.jsp");
                byte[] encoded = Files.readAllBytes(Paths.get(path));
                String navbar = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
                template = template.replace("<!--links-->", navbar);
            }
            else if(uRoleName.equals("admin")){
                String path = context.getRealPath("/templates/adminNavigationBar.jsp");
                byte[] encoded = Files.readAllBytes(Paths.get(path));
                String navbar = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
                template = template.replace("<!--links-->", navbar);
            }
            else if(uRoleName.equals("contributor")){
                String path = context.getRealPath("/templates/contributorNavigationBar.jsp");
                byte[] encoded = Files.readAllBytes(Paths.get(path));
                String navbar = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
                template = template.replace("<!--links-->", navbar);
            }
            
            String login = "<li><a href=\"#\">"+lm.getUser().getUsername()+"</a></li>\n" +
"                \n" +
"                <li>\n" +
"                    <form action=\"LogOutServlet\" method=\"POST\">\n" +
"                        <button type=\"submit\" class=\"btn btn-default navbar-btn\" name=\"logout\" value=\"logout\">Log out</button>\n" +
"                    </form>\n" +
"                </li>";
            template = template.replace("<!--login-->", login);
        }
        else{
            template = template.replace("<!--login-->", "<li><a href=\"logIn.jsp\">Log in</a></li>");
        }
        return template;
    }
}
