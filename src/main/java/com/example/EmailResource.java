package com.example;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

@Path("/email")
@Singleton
public class EmailResource {
  @POST
  @Consumes("application/json")
  @Produces("application/json") 
  public Response addExample(Email mail) {
    
    Properties props = new Properties();
    props.put("mail.smtp.host", "localhost");
    props.put("mail.smtp.port", "5000");
    Session session = Session.getInstance(props);
    try {
      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("test@example.com"));
      InternetAddress[] address = {new InternetAddress(mail.getTo())};
      msg.setRecipients(Message.RecipientType.TO, address);
      msg.setSubject(mail.getSubject());
      msg.setText(mail.getBody());
      Transport.send(msg);
    } catch (MessagingException e) {
      return Response.status(500).build();
    }
    
    return Response.ok().build();
  } 
}
