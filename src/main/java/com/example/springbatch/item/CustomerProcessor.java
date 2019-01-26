package com.example.springbatch.item;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.springbatch.model.Customer;
/**
 * @author 86211
 */
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerProcessor.class);

    @Override
    public Customer process(Customer item) throws Exception {
        LOGGER.info("CustomerProcessor has done. Customer is " + item);
        item.setUpdateTimeStamp(LocalDateTime.now());
        return item;
    }

}
