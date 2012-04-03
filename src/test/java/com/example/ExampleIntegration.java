package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.util.ajax.JSON;

public class ExampleIntegration {

    /** URL */
    private static final String URL = "http://localhost:8080/rest/example";
  
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
     * Test method for {@link com.example.Auth#login()}.
     */
    @Test
    public void testExample() {
        try {
          HttpPost post = new HttpPost(URL);
          
          post.addHeader("Content-Type", "application/json");
          
          post.setEntity(new StringEntity("{\"title\":\"test\"}"));
          
          HttpResponse response = client.execute(post);
          assertEquals(200, response.getStatusLine().getStatusCode());
          Map<String, Object> entity = (Map<String, Object>)JSON.parse(response.getEntity().getContent());
          
          assertEquals("test", entity.get("title"));
          
          
          HttpGet get = new HttpGet("http://localhost:8080/rest/example/" + entity.get("id"));
                    
          get.addHeader("Content-Type", "application/json");
          response = client.execute(get);
          assertEquals(200, response.getStatusLine().getStatusCode());
          
          Map<String, Object> gotEntity = (Map<String, Object>)JSON.parse(response.getEntity().getContent());
          
          assertEquals(entity.get("title"), gotEntity.get("title"));           
        } catch (Exception e) {
            fail(ERR_MSG + e);
        }
    }
}
