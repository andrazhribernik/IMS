/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;

/**
 *
 * @author andrazhribernik
 */
public class TemplatingManagement {
    public static String getTemplateWithContent(ServletContext context,String content) throws IOException{

        String path = context.getRealPath("/templates/index.txt");
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        String template = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
        template = template.replace("<!--TemplateContent-->", content);
        return template;

    }
}
