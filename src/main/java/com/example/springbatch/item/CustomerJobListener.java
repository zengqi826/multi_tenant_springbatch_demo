package com.example.springbatch.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import com.example.springbatch.configuration.TenantContext;
/**
 * @author 86211
 */
public class CustomerJobListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        String tenant = jobExecution.getJobParameters().getString("tenant");
        MDC.put("TENANT", tenant.toUpperCase());
        MDC.put("THREADID", Long.toString(Thread.currentThread().getId()));
        TenantContext.setCurrentTenant(tenant);
        LOGGER.info("Start the " + jobExecution.getJobInstance().getJobName() + ", jobExcutionId is "
                + jobExecution.getId());

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info(
                "End the " + jobExecution.getJobInstance().getJobName() + ", jobExcutionId is " + jobExecution.getId());
        MDC.clear();
    }

}
