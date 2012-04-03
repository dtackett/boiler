package com.example;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class Root {
  @Context UriInfo uriInfo;
  
  @GET
  public Response getRoot() {
    UriBuilder ub = uriInfo.getAbsolutePathBuilder();
    URI userUri = ub.path(Auth.class).build();
    
    return Response.ok(userUri.toString()).build();
  }
}
