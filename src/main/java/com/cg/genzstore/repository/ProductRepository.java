package com.cg.genzstore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cg.genzstore.model.entity.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}