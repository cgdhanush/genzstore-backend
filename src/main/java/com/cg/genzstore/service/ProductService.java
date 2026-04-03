package com.cg.genzstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.genzstore.model.entity.Product;
import com.cg.genzstore.repository.ProductRepository;
import com.cg.genzstore.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
