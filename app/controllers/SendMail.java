package controllers;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 *
 * @author VAIO
 */

public class SendMail {

    /**
     * @param args the command line arguments
     */
    
    public SendMail(String destination, String text, String url){
        send(destination, text, url);
    }
    
    public void send(String destination, String text,  String url){
        try{
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            /*Cambiar por el mail de tecnogi*/
            props.setProperty("mail.smtp.user", "technogipcprojo@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("technogipcprojo@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            message.setSubject("Confirm Client Registration Portal Crowd Providing");
            message.setText(" <html><head></head><body> <h4>"+text+"</h4><a href='"+url+"'>Save Registration</a></body></html>","ISO-8859-1","html");
            
            Transport t = session.getTransport("smtp");
            t.connect("technogipcprojo@gmail.com","portalcrowd123");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            
        }catch(Exception e){
            
        }
        
    }
}
