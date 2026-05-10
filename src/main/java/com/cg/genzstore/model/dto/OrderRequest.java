package com.cg.genzstore.model.dto;

import java.util.List;

import com.cg.genzstore.model.entity.OrderProduct;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderRequest {
    private List<OrderProduct> products;
    private double totalAmount;

}