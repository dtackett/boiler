package com.example;

import java.util.HashMap;
import java.util.Map;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.ShiroModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.UserDAO;
import com.example.data.User;
import com.example.util.UserUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;


public class BoilerServletConfig extends GuiceServletContextListener {
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  private ServletContext servletContext;
  
  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    servletContext = servletContextEvent.getServletContext();
    super.contextInitialized(servletContextEvent);
    
    // Create a default user
    User user = new User();    
    user.setEmail("lonestarr");
    UserUtil.setupNewPassword(user, "vespa".toCharArray());
    
    Injector injector = (Injector)servletContext.getAttribute(Injector.class.getName());
    UnitOfWork unitOfWork = injector.getInstance(UnitOfWork.class);
    unitOfWork.begin();
    UserDAO userDAO = injector.getInstance(UserDAO.class);    
    userDAO.create(user);
    unitOfWork.end();
    
    log.info("Created user");        
  }
  
  @Override
  protected Injector getInjector() {
    Injector injector = Guice.createInjector(new JerseyServletModule() {
      @Override
      protected void configureServlets() {
        install(new JpaPersistModule("boilerJPAUnit"));        
        
//        filter("/*").through(PersistFilter.class);
        // Guice/Shiro compatibility filter
        filter("/*").through(GuiceShiroFilter.class);
        
        Map<String, String> params = new HashMap<String, String>();
        // Turn on pojo mapping for json
        params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        // Scan packages for Jersey resource endpoints
        params.put(PackagesResourceConfig.PROPERTY_PACKAGES, "com.example.resource");    
//        params.put("javax.ws.rs.Application", "com.example.MainJerseyApplication");        
        serve("/rest/*").with(GuiceContainer.class, params);    
      }
    }, new ShiroWebModule(servletContext) {
      @Override
      protected void configureShiroWeb() {
        bindRealm().to(BoilerRealm.class);
      }
    });
    
    PersistService service = injector.getInstance(PersistService.class);
    service.start();
    
    SecurityManager securityManager = injector.getInstance(SecurityManager.class);
    SecurityUtils.setSecurityManager(securityManager);
    
    return injector;
  }
}