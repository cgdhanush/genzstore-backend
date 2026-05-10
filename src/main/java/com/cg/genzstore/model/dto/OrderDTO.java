package com.cg.genzstore.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cg.genzstore.model.entity.OrderProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class OrderDTO {

    private String id;
    private List<OrderProduct> products;
    private double totalAmount;
    private String status;
    private LocalDateTime orderDate;
    
}