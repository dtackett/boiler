package com.example;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.UserDAO;
import com.example.data.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BoilerRealm extends AuthorizingRealm {
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  @Inject UserDAO userDAO;
  
  public BoilerRealm() {
    HashedCredentialsMatcher cm = new HashedCredentialsMatcher();
    cm.setHashAlgorithmName("SHA-512");
    cm.setHashIterations(1024);
    cm.setStoredCredentialsHexEncoded(false);
    
    setCredentialsMatcher(cm);
    
    log.info("Credential Matcher configured");
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
    log.info("doGetAuthorizationInfo: " + userDAO);
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
      throws AuthenticationException {
    try {
      UsernamePasswordToken userToken = (UsernamePasswordToken)token;
      User user = userDAO.getByEmail(userToken.getUsername());
      return createAuthenticationInfo(user);
    }
    catch( Exception e ) {
      throw new AuthenticationException("Could not authenticate user.", e);
    }    
  }

  private AuthenticationInfo createAuthenticationInfo( User user ) {
    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
    info.setPrincipals(new SimplePrincipalCollection(user.getId(), getName()));
    info.setCredentials(user.getPassword());
    info.setCredentialsSalt(new SimpleByteSource(user.getSalt()));
    return info;
  }  
}
