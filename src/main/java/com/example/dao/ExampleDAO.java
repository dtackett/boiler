package com.example.dao;

import java.util.List;

import javax.persistence.Query;

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
  
  private String buildSearchQuery(String search) {
    StringBuilder qs = new StringBuilder();
    
    if (search != null) {
      qs.append("where lower(e.title) like :search");
    }
    
    return qs.toString();
  }
  
  /**
   * Setup the query parameters for the given query.
   */
  private Query setupQueryParameters(Query query, String search, Integer page, Integer pageSize) {
    if (search != null) query.setParameter("search", search.toLowerCase());
    
    query.setFirstResult(page*pageSize).setMaxResults(pageSize);
    
    return query;
  }
  
  @Transactional
  public Long countExamples(String search) {
    StringBuilder qs = new StringBuilder();    
    qs.append("select count(e.id) from Example e ");
    
    qs.append(buildSearchQuery(search));
    
    Query query = provider.get().createQuery(qs.toString());
    
    query = setupQueryParameters(query, search, null, null);
    
    return (Long)query.getSingleResult();
  }
  
  @Transactional
  public List<Example> list(String search, String sort, Integer page, Integer pageSize) {
    StringBuilder qs = new StringBuilder();    
    qs.append("select e from Example e ");
    
    // Setup search query
    qs.append(buildSearchQuery(search));
    
    // Setup sort
    if (sort != null && sort.equalsIgnoreCase("title")) {
      qs.append(" order by e.title desc");
    }
    
    Query query = provider.get().createQuery(qs.toString());
    
    query = setupQueryParameters(query, search, page, pageSize);
    
    return query.getResultList();
  }
  
}
