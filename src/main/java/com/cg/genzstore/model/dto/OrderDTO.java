package com.cg.genzstore.model.dto;

import java.util.List;

import com.cg.genzstore.model.entity.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class OrderDTO {

    private String id;

    private List<Product> products;

    private double totalAmount;

    private String status;
}