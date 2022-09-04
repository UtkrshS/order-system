package com.utk.order.system.ordersystem.repository;

import com.utk.order.system.ordersystem.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Product, String> {

    @Query("{ emailId:?0 }")
    Optional<Product> findByEmailId(String emailId);

}
