package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.google.inject.servlet.RequestScoped;

@Path("/test")
@RequestScoped  
public class Main {

  @GET
  @Produces("text/html")
  public String getMessage() {
    Subject currentUser = SecurityUtils.getSubject();
    
    if (!currentUser.isAuthenticated()) {
      return "who are you?";
    } else {    
      return "hello " + currentUser.getPrincipal();
    }
  }
}