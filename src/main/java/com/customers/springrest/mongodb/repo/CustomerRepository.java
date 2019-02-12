package com.customers.springrest.mongodb.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.customers.springrest.mongodb.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{
	List<Customer> findByType(String type);
}
