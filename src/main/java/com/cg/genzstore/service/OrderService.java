package com.cg.genzstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.genzstore.model.dto.OrderDTO;
import com.cg.genzstore.model.entity.Order;
import com.cg.genzstore.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public OrderDTO getOrderById(String id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderDTO.builder()
                .id(order.getId())
                .products(order.getProducts())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }

    public List<OrderDTO> getOrdersByUser(String userId) {

        List<Order> orders = repository.findByUserIdOrderByOrderDateDesc(userId);

        return orders.stream()
                .map(order -> OrderDTO.builder()
                        .id(order.getId())
                        .products(order.getProducts())
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus())
                        .build())
                .toList();
    }
}