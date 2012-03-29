package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class MyGuiceServletConfig extends GuiceServletContextListener {
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  @Override
  protected Injector getInjector() {
    log.info("getInjector called");
    return Guice.createInjector(new ServletModule());
  }
}