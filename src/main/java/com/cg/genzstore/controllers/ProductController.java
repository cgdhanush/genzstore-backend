package com.cg.genzstore.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.genzstore.model.dto.ProductDTO;
import com.cg.genzstore.model.entity.Product;
import com.cg.genzstore.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl());
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody Product product) {
        Product p = productService.createProduct(product);
        return convertToDTO(p);
    }

    @GetMapping
    public List<ProductDTO> getAllProduct() {
        return productService.getAllProduct().stream()
                .map(p -> convertToDTO(p))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable String name) {
        Product p = productService.getProductByName(name);
        return convertToDTO(p);
    }
}
