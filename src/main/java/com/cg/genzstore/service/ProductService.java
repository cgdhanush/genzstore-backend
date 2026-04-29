package com.cg.genzstore.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cg.genzstore.model.entity.Product;
import com.cg.genzstore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private FileService fileService;

    public Product saveProduct(String name, String desc, String category, Integer price, MultipartFile file) throws Exception {

        Product product = new Product();
        product.setName(name);
        product.setDescription(desc);
        product.setCategory(category);
        product.setPrice(price);

        String imageId = fileService.uploadImage(file);
        product.setImageId(imageId);

        return repository.save(product);
    }

    public byte[] getProductImage(String productId) throws IOException {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return fileService.getImage(product.getImageId());
    }

    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    public Product getProductByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // public Product updateProduct(String name, Product updatedProduct) {
    // Product existing = getProductByName(name);

    // existing.setName(updatedProduct.getName());
    // existing.setDescription(updatedProduct.getDescription());
    // existing.setPrice(updatedProduct.getPrice());
    // existing.setImageUrl(updatedProduct.getImageUrl());

    // return repository.save(existing);
    // }

    // public void deleteProduct(String name) {
    // Product product = getProductByName(name);
    // repository.delete(product);
    // }
}
