package com.cg.genzstore.model.entity;

import java.time.LocalDateTime;

public class Order {
    
    private Long id;
    private double totalAmount;
    private String status; // PLACED, SHIPPED, DELIVERED
    private LocalDateTime createdAt;
}
