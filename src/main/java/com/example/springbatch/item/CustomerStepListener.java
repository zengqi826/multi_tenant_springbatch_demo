package com.example.springbatch.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import com.example.springbatch.configuration.TenantContext;
/**
 * @author 86211
 */
public class CustomerStepListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStepListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        MDC.put("TENANT", TenantContext.getCurrentTenant().toUpperCase());
        MDC.put("THREADID", Long.toString(Thread.currentThread().getId()));
        LOGGER.info("Start the " + stepExecution.getStepName() + ", stepExcutionId is " + stepExecution.getId());

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("End the " + stepExecution.getStepName() + ", stepExcutionId is " + stepExecution.getId());
        return stepExecution.getExitStatus();
    }

}
