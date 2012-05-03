package com.example;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ExampleDAO {
  @Inject Provider<EntityManager> provider;
  
  @Transactional
  public List<Example> list() {
    return provider.get().createQuery("select e from Example e").getResultList();
  }

  @Transactional
  public Example get(Long id) {
    return provider.get().find(Example.class, id);
  }
  
  @Transactional
  public void delete(Example entity) {
    provider.get().remove(entity);
  }
  
  @Transactional
  public void create(Example entity) {
    provider.get().persist(entity);
  }
}
