package com.example.springbatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbatch.model.Customer;
/**
 * @author 86211
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
