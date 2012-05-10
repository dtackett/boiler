package com.example;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.ExampleDAO;
import com.example.data.Example;
import com.google.inject.Inject;
import com.google.inject.persist.UnitOfWork;

public class ExampleJob implements Job {
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  @Inject ExampleDAO dao;
  @Inject UnitOfWork unitOfWork;
  
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.debug("Job ran");
    
    unitOfWork.begin();
    try {
      Example newExample = new Example();
      newExample.setTitle("Created via Job");
      dao.create(newExample);
    } finally {
      unitOfWork.end();
    }
  }

}
