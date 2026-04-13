package com.cg.genzstore.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.genzstore.model.entity.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
}