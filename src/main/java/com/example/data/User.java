package com.example.data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "USER" )
public class User extends BaseEntity {
  private String email;
  private String password;
  private String salt;
  
  public User() {
    
  }
  
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}