package com.example;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

@Path("/example")
@RequestScoped  
public class ExampleResource {
  
  @Inject Provider<EntityManager> provider;

  @GET
  @Produces("application/json")
  public String getExamples( ) {
    return null;
  }
 
  @GET
  @Path("/{exampleId}")
  @Produces("application/json") 
  public Example getExample(@PathParam("exampleId") Long id) {
    provider.get().getTransaction().begin();
    
    Example exp = provider.get().find(Example.class, id);
    
    provider.get().getTransaction().commit();    
   
    return exp;
  }
 
  @POST
  @Consumes("application/json")
  @Produces("application/json") 
  public Example addExample(Example rep) {
        
    provider.get().getTransaction().begin();
    
    Example exp = new Example();
    exp.setTitle(rep.getTitle());
    
    provider.get().persist(exp);
    
    provider.get().getTransaction().commit();
   
    return exp;
  } 
}