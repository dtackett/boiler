package com.example.resource;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

@Path("/")
@Singleton
public class Root {
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  @Context UriInfo uriInfo;
  
  @GET
  public Response getRoot() {
    UriBuilder ub = uriInfo.getAbsolutePathBuilder();
    URI userUri = ub.path(Auth.class).build();
    
    return Response.ok(userUri.toString()).build();
  }
}
