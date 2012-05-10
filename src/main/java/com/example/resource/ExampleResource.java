package com.example.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.example.dao.ExampleDAO;
import com.example.data.Example;
import com.sun.jersey.spi.resource.Singleton;

@Path("/example")
@Singleton
public class ExampleResource {
  
  @Inject ExampleDAO dao;

//  @GET
//  @Produces("application/json")
//  public List<Example> getExamples( ) {
//    List<Example> examples = dao.list();
//    
//    return examples;
//  }
 
  @GET
  @Path("/{exampleId}")
  @Produces("application/json")
  public Example getExample(@PathParam("exampleId") Long id) {
    Example exp = dao.get(id);
    
    if (exp == null) {
      throw new WebApplicationException(404);
    }
   
    return exp;
  }
  
  @GET
  @Produces("application/json")
  public Response paginateExamples( @DefaultValue("0") @QueryParam("page") Integer page, @DefaultValue("0") @QueryParam("size") Integer pageSize, @QueryParam("search") String searchParam, @QueryParam("sort") String sortField ) {
    long pageCount = 0;
    
    List<Example> examples = dao.list(searchParam, sortField, page, pageSize);
    
    // Find the max number of pages based on the current page size
    if (pageSize != null && pageSize > 0) {
      pageCount = (long)Math.ceil((float)dao.countExamples(searchParam)/(float)pageSize);
    }
    
    ResponseBuilder builder = Response.ok(examples);
    
    if (pageSize > 0) {
      builder.header("X-pages", pageCount);
    }
    
    return builder.build();    
  }  
  
  @DELETE
  @Path("/{exampleId}")
  @Produces("application/json")
  public Example deleteExample(@PathParam("exampleId") Long id) {
    Example exp = dao.get(id);
    
    if (exp != null)
      dao.deleteById(id);
    
    if (exp == null) {
      throw new WebApplicationException(404);
    }
   
    return exp;
  }  
 
  @POST
  @Consumes("application/json")
  @Produces("application/json") 
  public Example addExample(Example rep) {
    
    Example exp = new Example();
    exp.setTitle(rep.getTitle());
    
    dao.create(exp);
   
    return exp;
  } 
}