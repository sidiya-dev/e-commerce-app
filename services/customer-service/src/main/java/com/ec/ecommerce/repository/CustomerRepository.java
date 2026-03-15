package com.ec.ecommerce.repository;

import com.ec.ecommerce.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String id);
}
