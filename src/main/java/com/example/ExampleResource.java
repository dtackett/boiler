package com.example;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;

@Path("/example")
@RequestScoped  
public class ExampleResource {
  
  @Inject Provider<EntityManager> provider;

  @GET
  @Produces("application/json")
  public List<Example> getExamples( ) {
    provider.get().getTransaction().begin();
    
    List<Example> examples = provider.get().createQuery("select e from Example e").getResultList();
    
    provider.get().getTransaction().commit();
    
    return examples;
  }
 
  @GET
  @Path("/{exampleId}")
  @Produces("application/json") 
  public Example getExample(@PathParam("exampleId") Long id) {
    provider.get().getTransaction().begin();
    
    Example exp = provider.get().find(Example.class, id);
    
    provider.get().getTransaction().commit();
    
    if (exp == null) {
      throw new WebApplicationException(404);
    }
   
    return exp;
  }
  
  @DELETE
  @Path("/{exampleId}")
  @Produces("application/json") 
  public Example deleteExample(@PathParam("exampleId") Long id) {
    provider.get().getTransaction().begin();
    
    Example exp = provider.get().find(Example.class, id);
    
    if (exp != null)
      provider.get().remove(exp);
    
    provider.get().getTransaction().commit();
    
    if (exp == null) {
      throw new WebApplicationException(404);
    }
   
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