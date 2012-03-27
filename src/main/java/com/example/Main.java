package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

@Path("/test")
public class Main {

 @GET
 @Produces("text/html")
 public String getMessage( ) {
  return "hello";
 }
}