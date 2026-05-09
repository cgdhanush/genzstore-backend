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

    public Product saveProduct(String name, String desc, String category, Integer price, MultipartFile file)
            throws Exception {

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

    public List<Product> getAllProducts(
            String category,
            Double minPrice,
            Double maxPrice,
            Integer limit) {
        List<Product> products = repository.findAll();

        if (category != null) {
            products = products.stream()
                    .filter(p -> p.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        if (minPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= minPrice)
                    .toList();
        }

        if (maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() <= maxPrice)
                    .toList();
        }

        if (limit != null && limit > 0 && limit < products.size()) {
            products = products.subList(0, limit);
        }

        return products;
    }

    public Product getProductById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
