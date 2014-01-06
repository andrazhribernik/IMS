/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *This class provide method for sending Gmail email. 
 * @author andrazhribernik
 */
public class SendMailTLS {
    //Gmail account credentials
    final String username = "ah.cs3510@gmail.com";
    final String password = "andrazhribernik";
    
    /**
     * Send an email.
     * @param targetEmail email address of recipient 
     * @param content content of email
     */
    public void sendEmail(String targetEmail, String content){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                }
          });

        try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("reset-password@ims.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(targetEmail));
                message.setSubject("New password IMS");
                //message.setText(content);
                message.setContent(content, "text/html");

                Transport.send(message);


        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
        catch (Exception e){}
    }
}
