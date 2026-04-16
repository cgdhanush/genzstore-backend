package com.cg.genzstore.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cg.genzstore.model.dto.ProductDTO;
import com.cg.genzstore.model.dto.ProductRequestDTO;
import com.cg.genzstore.model.entity.Product;
import com.cg.genzstore.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCategory());
    }

    @PostMapping(value = "/api/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam Integer price,
            @RequestParam MultipartFile file
    ) throws Exception {

        return productService.saveProduct(name, description, category, price, file);
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

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String name) {

        Product product = productService.getProductByName(name);

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg")
                .body(product.getImage().getData());
    }
}
