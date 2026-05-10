package com.cg.genzstore.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProduct {
    private Product product;
    private int quantity;
}