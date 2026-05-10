package com.cg.genzstore.model.entity;

import java.time.LocalDateTime;
import java.util.List;

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
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private List<OrderProduct> products;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;
}