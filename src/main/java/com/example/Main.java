package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/test")
public class Main {
  @GET
  @Produces("text/html")
  public String getMessage( ) {
    return "hello";
  }
}