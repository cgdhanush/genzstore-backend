package com.cg.genzstore.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.genzstore.model.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);

}