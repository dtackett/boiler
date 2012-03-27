package com.meltmedia;
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
  
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
 
/**
 * Test class for {@link MyServlet}
 * <p>
 * This is an integration test.
 * <p>
 * In order to pass these tests within an IDE, you have to first run Jetty. 
 * This is configured for you via maven, if you run the command:
 * 
 * <pre>
 * mvn jetty:run
 * </pre>
 * 
 * Alternatively, you can just run the maven command:
 * <pre>
 * mvn integration-test
 * </pre>
 * 
 * and maven will do everything for you (start jetty, run test, stop jetty).
 * <p>
 * Make sure the literal URL below matches that defined in pom.xml
 */
public class SimpleIntegration {
 
    /** URL */
    private static final String URL = "http://localhost:8080/rest/test";
 
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
     * Test method for {@link com.example.Main#getMessage()}.
     */
    @Test
    public void testGetMessage() {
        try {
            HttpResponse response = client.execute(new HttpGet(URL));
            assertEquals(200, response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            fail(ERR_MSG + e);
        }
    }
}