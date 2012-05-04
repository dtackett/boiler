package com.example.dao;

import java.util.List;

import com.example.data.IEntity;

/**
 * Base interface for DAO services.
 * 
 * @author Devon Tackett
 * 
 */
public interface IDAO<E extends IEntity> {  
  public E get(Long id);
  public E update(E entity);
  public E create(E entity);
  public E refresh(E entity);
  public Boolean lock(E entity);
  public List<E> list();
  public List<E> list(int page, int limit);         // List entities (with pagination support)
  public Boolean delete(E entity);
  public Boolean deleteById(long id);
}
