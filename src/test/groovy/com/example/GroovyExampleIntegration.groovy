package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import groovyx.net.http.RESTClient

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.util.ajax.JSON;

public class GroovyExampleIntegration {
    /** URL */
    private static final String URL = "http://localhost:8080/rest/example";
  
    /**
     * Test method for {@link com.example.ExampleResource#addExample()} and {@link com.example.ExampleResource#getExample()}.
     */
    @Test
    public void testCreateExample() {
        def http = new RESTClient( URL )
        
        // Create the resource
        def resp = http.post(body: [title:"test"],
          requestContentType: "application/json")
        
        assert resp.status == 200
        assert resp.data.id == 1
        assert resp.data.title == "test"
        
        // Get the resource
        resp = http.get(path: '/rest/example/'+resp.data.id,
          requestContentType: "application/json")
        
        assert resp.status == 200
        assert resp.data.title == "test"

        // Delete the entry (so as not to pollute the rest of the tests)
        http.delete(path: '/rest/example/'+resp.data.id,
        requestContentType: "application/json")
    }
    
    /**
    * Test method for {@link com.example.ExampleResource#getExample()} for an invalid resource.
    */
   @Test
   public void testFindNonExistentExample() {
       def http = new RESTClient( URL )

       try {
         def resp = http.get(path: '/rest/example/-1',
           requestContentType: "application/json")
         assert false, "Expected exception"
       } catch ( ex ) {
         assert ex.response.status == 404 
       }
   }
    
    /**
    * Test method for {@link com.example.ExampleResource#getExamples()}.
    */
   @Test
   public void testListExample() {
       def http = new RESTClient( URL )
       
       def resp = http.get(requestContentType: "application/json")
       
       assert resp.status == 200
       assert resp.data.asList.size == 0
   }
   
   /**
   * Test method for {@link com.example.ExampleResource#addExample()} and {@link com.example.ExampleResource#getExample()}.
   */
  @Test
  public void testDeleteExample() {
      def http = new RESTClient( URL )
      
      // Create the resource
      def resp = http.post(body: [title:"delete me"],
        requestContentType: "application/json")
      
      assert resp.status == 200
      assert resp.data.title == "delete me"
      
      // Get the resource
      resp = http.delete(path: '/rest/example/'+resp.data.id,
        requestContentType: "application/json")
      
      assert resp.status == 200
      
      try {
        resp = http.get(path: '/rest/example/'+resp.data.id,
          requestContentType: "application/json")
        assert false, "Expected exception"
      } catch ( ex ) {
        assert ex.response.status == 404
      }
  }
}