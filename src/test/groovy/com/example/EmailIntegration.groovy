package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import groovyx.net.http.RESTClient

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.util.ajax.JSON;

import com.dumbster.smtp.SimpleSmtpServer

public class EmailIntegration {
    /** URL */
    private static final String URL = "http://localhost:8080/rest/email";
  
    /**
     * Test method for sending email.
     */
    @Test
    public void testCreateExample() {
        def server = SimpleSmtpServer.start(5000)
      
        def http = new RESTClient( URL )
        
        // Create the resource
        def resp = http.post(body: [to:"test@example.com", subject:"test subject", body:"test body"],
          requestContentType: "application/json")
        
        assert resp.status == 200
        
        server.stop()
        
        assert server.receivedEmail.toList().size() == 1
        
        // a little dump to see what's in the email :)
        server.receivedEmail.each { println it }
    }
}