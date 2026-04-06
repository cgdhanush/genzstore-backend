package com.cg.genzstore.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private Integer price;
    private String imageUrl;
}
