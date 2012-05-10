package com.example.dao;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.data.Example;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;


public class ExampleDAOTest {
  private PersistService service;
  
  private ExampleDAO dao;
  
  @Before
  public void setupContext() throws IllegalAccessException, NoSuchFieldException {
    Injector injector = Guice.createInjector(new JpaPersistModule("unitTest"));
    
    service = injector.getInstance(PersistService.class);
    service.start();    
    
    dao = injector.getInstance(ExampleDAO.class);
  }
  
  @After
  public void tearDown() {
    service.stop();
  }   
  
  /**
   * Extremely trivial unit test of dao with in memory database.
   */
  @Test
  public void simplePersistTest() {
    Example testExample = new Example();
    testExample.setTitle("Example Unit Test Title");
    
    dao.create(testExample);
    
    Assert.assertNotNull(testExample.getId());
    
    Example retrievedExample = dao.get(testExample.getId());
    
    Assert.assertEquals(testExample.getTitle(), retrievedExample.getTitle());
  }
  
  /**
   * Another trivial test for the list method.
   */
  @Test
  public void testEmptyList() {
    List<Example> entityList = dao.list();
    
    Assert.assertEquals(0, entityList.size());
  }
}
