package com.example.springbatch.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort.Direction;

import com.example.springbatch.item.CustomerJobListener;
import com.example.springbatch.item.CustomerProcessor;
import com.example.springbatch.item.CustomerStepListener;
import com.example.springbatch.model.Customer;
import com.example.springbatch.repository.CustomerRepository;
/**
 * @author 86211
 */
@Configuration
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    CustomerRepository repository;
    
    private static final String READER_METHOD_NAME = "findAll";
    
    private static final String WRITER_METHOD_NAME = "save";

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ItemReader<Customer> itemReader() {
        Map<String, Direction> sort = new HashMap<String, Direction>(1);
        sort.put("id", Direction.ASC);
        return new RepositoryItemReaderBuilder<Customer>().name("repositoryItemReader").saveState(false)
                .sorts(sort).methodName(READER_METHOD_NAME).pageSize(100).arguments(null).repository(repository).build();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ItemProcessor<Customer,Customer> itemProcessor() {
        return new CustomerProcessor();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ItemWriter<Customer> itemWriter() {
        return new RepositoryItemWriterBuilder<Customer>().methodName(WRITER_METHOD_NAME).repository(repository).build();
    }
    
    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new CustomerJobListener();
    }
    
    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new CustomerStepListener();
    }
}
