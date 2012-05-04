package com.example.dao;

import java.util.List;

import com.example.data.Example;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class ExampleDAO extends AbstractDAO<Example> {
  
  public ExampleDAO() {
    super(Example.class);
  }

  @Override
  @Transactional
  public Example get(Long id) {
    return super.get(id);
  }

  @Override
  @Transactional
  public Example create(Example entity) {
    return super.create(entity);
  }

  @Override
  @Transactional
  public Example update(Example entity) {
    return super.update(entity);
  }

  @Override
  @Transactional
  public Boolean delete(Example entity) {
    return super.delete(entity);
  }

  @Override
  @Transactional
  public Boolean deleteById(long id) {
    return super.deleteById(id);
  }

  @Override
  @Transactional
  public List<Example> list() {
    return super.list();
  }
  
  
}
