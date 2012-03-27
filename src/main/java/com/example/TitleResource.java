package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Path("/example")
public class TitleResource {

 @GET
 @Produces("application/json")
 public String getExamples( ) {
   return null;
 }
 
 @GET
 @Path("/{exampleId}")
 @Produces("application/json") 
 public Example getExample(@PathParam("exampleId") Long id) {
   Example exp = new Example();
   exp.setId(1L);
   exp.setTitle("Test");
   
   return exp;
 }
 
 @POST
 @Path("/{exampleId}")
 @Produces("application/json") 
 public Example addExample(@PathParam("exampleId") Long id) {
   Example exp = new Example();
   exp.setId(1L);
   exp.setTitle("Test");
   
   return exp;
 } 
}