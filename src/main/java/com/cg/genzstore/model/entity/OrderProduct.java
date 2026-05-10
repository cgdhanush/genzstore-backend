package com.cg.genzstore.model.entity;

import lombok.Data;

@Data
public class OrderProduct {
    private Product product;
    private int quantity;
}