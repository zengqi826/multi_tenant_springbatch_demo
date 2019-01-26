package com.example.springbatch.configuration;

import com.example.springbatch.model.Customer;
import com.example.springbatch.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 86211
 */
@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    private static final String DATASOURCE_TYPE = "mysql";

    private static final String STEP_NAME = "Step";

    private static final String JOB_NAME = "Job";

    private static final String FLOW_NAME = "Flow";

    private static final String READER_METHOD_NAME = "findAll";

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

/*    @Autowired
    ItemReader<Customer> itemReader;*/

    @Autowired
    CustomerRepository repository;

    @Autowired
    ItemProcessor<Customer, Customer> itemProcessor;

    @Autowired
    ItemWriter<Customer> itemWriter;

    @Autowired
    JobExecutionListener jobExecutionListener;

    @Autowired
    StepExecutionListener stepExecutionListener;

    @Scheduled(fixedDelayString = "${sheduledTask.fixedDelay}")
    public void excute() {
        List<String> tenants = TenantContext.getTenantList();
        LOGGER.info("Start sheduledTask.");
        tenants.stream().forEach(tenant -> {
            try {
                long threadId = Thread.currentThread().getId();
                initMDC(tenant.toUpperCase(), Long.toString(threadId), UUID.randomUUID().toString());
                LOGGER.debug("Excute " + tenant + " schduledTask, threadId is " + Long.toString(threadId));
                DataSource dataSource = (DataSource) TenantContext.getAllDataSources().get(tenant);
                JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
                jobRepositoryFactoryBean.setDataSource(dataSource);
                jobRepositoryFactoryBean.setDatabaseType(DATASOURCE_TYPE);
                jobRepositoryFactoryBean.setTransactionManager(transactionManager);
                jobRepositoryFactoryBean.afterPropertiesSet();
                JobRepository jobRepository = jobRepositoryFactoryBean.getObject();

                SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
                simpleJobLauncher.setJobRepository(jobRepository);
                simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
                simpleJobLauncher.afterPropertiesSet();

                Step firstStep = stepBuilderFactory.get(tenant + STEP_NAME).repository(jobRepository)
                        .<Customer, Customer>chunk(10).reader(reader()).processor(itemProcessor).writer(itemWriter)
                        .listener(stepExecutionListener).taskExecutor(new SimpleAsyncTaskExecutor()).throttleLimit(4)
                        .build();

                Flow firstFlow = new FlowBuilder<SimpleFlow>(tenant + FLOW_NAME).start(firstStep).build();

                Job job = jobBuilderFactory.get(tenant + JOB_NAME).repository(jobRepository)
                        .incrementer(new RunIdIncrementer()).listener(jobExecutionListener).start(firstFlow).end()
                        .build();

                JobParameters jobParameters = new JobParametersBuilder().addString("tenant", tenant)
                        .addLong("time", System.currentTimeMillis()).toJobParameters();

                JobExecution jobExecution = simpleJobLauncher.run(job, jobParameters);
                LOGGER.info("End ScheduledTask, jobExecutionId is " + jobExecution.getId());
            } catch (Exception e) {
                LOGGER.error("End " + tenant + " ScheduledTask with exception ,catch exception: " + e.getMessage(), e);
            } finally {
                MDC.clear();
                LOGGER.info("End " + tenant + " simpleJobLauncher ,threadId is " + Thread.currentThread().getId());
            }
        });
    }


    /**
     * use this in order to avoid vary thread to use the same ItemReader
     */
    private ItemReader<Customer> reader() {
        Map<String, Direction> sort = new HashMap<String, Direction>(1);
        sort.put("id", Direction.ASC);
        return new RepositoryItemReaderBuilder<Customer>().name("repositoryItemReader").saveState(false).sorts(sort)
                .methodName(READER_METHOD_NAME).pageSize(100).arguments(null).repository(repository).build();
    }

    private void initMDC(String tenant, String threadId, String uuid) {
        MDC.put("TENANT", tenant);
        MDC.put("THREADID", threadId);
        MDC.put("UUID", uuid);
    }

}
