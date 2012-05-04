package com.example.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

@Path("/auth")
public class Auth {
  
  @POST
  public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {    
    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
    
    Subject currentUser = SecurityUtils.getSubject();
    
    try {
      currentUser.login(token);
    } catch (UnknownAccountException uae) {
//        log.info("There is no user with username of " + token.getPrincipal());
    } catch (IncorrectCredentialsException ice) {
//        log.info("Password for account " + token.getPrincipal() + " was incorrect!");
    } catch (LockedAccountException lae) {
//        log.info("The account for username " + token.getPrincipal() + " is locked.  " +
//                "Please contact your administrator to unlock it.");
    }
    // ... catch more exceptions here (maybe custom ones specific to your application?
    catch (AuthenticationException ae) {
        //unexpected condition?  error?
    }    
    
    if (currentUser.isAuthenticated()) {
      return Response.status(204).build();
    } else {
      return Response.status(401).build();
    }
  }
}
