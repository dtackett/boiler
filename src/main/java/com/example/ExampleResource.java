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
import com.google.inject.persist.Transactional;
import com.google.inject.servlet.RequestScoped;

@Path("/example")
public class ExampleResource {
  
  @Inject EntityManager provider;

  @GET
  @Produces("application/json")
  @Transactional
  public List<Example> getExamples( ) {
    List<Example> examples = provider.createQuery("select e from Example e").getResultList();
    
    return examples;
  }
 
  @GET
  @Path("/{exampleId}")
  @Produces("application/json")
  @Transactional
  public Example getExample(@PathParam("exampleId") Long id) {
    Example exp = provider.find(Example.class, id);
    
    if (exp == null) {
      throw new WebApplicationException(404);
    }
   
    return exp;
  }
  
  @DELETE
  @Path("/{exampleId}")
  @Produces("application/json")
  @Transactional  
  public Example deleteExample(@PathParam("exampleId") Long id) {
    Example exp = provider.find(Example.class, id);
    
    if (exp != null)
      provider.remove(exp);
    
    if (exp == null) {
      throw new WebApplicationException(404);
    }
   
    return exp;
  }  
 
  @POST
  @Consumes("application/json")
  @Produces("application/json") 
  @Transactional  
  public Example addExample(Example rep) {
    
    Example exp = new Example();
    exp.setTitle(rep.getTitle());
    
    provider.persist(exp);
   
    return exp;
  } 
}