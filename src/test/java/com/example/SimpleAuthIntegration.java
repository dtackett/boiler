package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleAuthIntegration {
  /** URL */
  private static final String URL = "http://localhost:8080/rest/auth";

  private static final String ERR_MSG = 
      "Unexpected exception in test. Is Jetty Running at "+URL+" ? ->";

  protected HttpClient client;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() {
      client = new DefaultHttpClient();
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link com.example.resource.Auth#login()}.
   */
  @Test
  public void testAuth() {
      try {
        HttpPost post = new HttpPost(URL + "?username=lonestarr&password=vespa");
//        HttpParams params = new BasicHttpParams();
//        
//        params.setParameter("username", "lonestarr");
//        params.setParameter("password", "vespa");
//        
//        post.setParams(params);
        
        HttpResponse response = client.execute(post);
        assertEquals(204, response.getStatusLine().getStatusCode());
        
        response = client.execute(new HttpGet("http://localhost:8080/rest/test"));
        assertEquals(200, response.getStatusLine().getStatusCode());
        
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        assertEquals("hello 1", rd.readLine());                
      } catch (Exception e) {
          fail(ERR_MSG + e);
      }
  }
  
  /**
   * Test method for {@link com.example.resource.Auth#login()}.
   */
  @Test
  public void testInvalidAuth() {
      try {
        HttpPost post = new HttpPost(URL + "?username=bobba&password=thefett");
//        HttpParams params = new BasicHttpParams();
//        
//        params.setParameter("username", "bobba");
//        params.setParameter("password", "thefett");
//        
//        post.setParams(params);
        HttpResponse response = client.execute(post);
        
        assertEquals(401, response.getStatusLine().getStatusCode());        
      } catch (Exception e) {
          fail(ERR_MSG + e);
      }
  }    
}
