package com.cg.genzstore.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cg.genzstore.model.entity.Product;
import com.cg.genzstore.service.FileService;
import com.cg.genzstore.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;;

    @PostMapping(value = "/api/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product createProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam Integer price,
            @RequestParam MultipartFile file) throws Exception {

        return productService.saveProduct(name, description, category, price, file);
    }

    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer limit) {
        return productService.getAllProducts(category, minPrice, maxPrice, limit);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.getProductById(id);

    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {

        byte[] image = fileService.getImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(image);
    }

    @GetMapping("/image/random")
    public ResponseEntity<byte[]> getRandomImage() throws IOException {

        List<Product> products = productService.getAllProducts(null, null, null, null);

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Product randomProduct = products.get(
                ThreadLocalRandom.current().nextInt(products.size()));

        byte[] image = fileService.getImage(randomProduct.getImageId());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(image);
    }

    @GetMapping("/categories")
    public Set<String> getAllCategories() {
        List<Product> products = productService.getAllProducts(null, null, null, null);

        return products.stream()
                .map(Product::getCategory)
                .filter(category -> category != null && !category.isBlank())
                .collect(Collectors.toSet());
    }
}
