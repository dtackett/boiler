package com.example;

import org.quartz.Job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

import com.google.inject.Inject;
import com.google.inject.Injector;

final class BoilerJobFactory implements JobFactory {
    @Inject private Injector guice;

    @Override
    public Job newJob(TriggerFiredBundle trigger, Scheduler scheduler) throws SchedulerException {
      return guice.getInstance(trigger.getJobDetail().getJobClass());
    }
 
}