package com.cg.genzstore.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.genzstore.model.dto.OrderDTO;
import com.cg.genzstore.model.dto.OrderRequest;
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
        return mapToDTO(order);
    }

    public List<OrderDTO> getOrdersByUser(String userId) {

        List<Order> orders = repository.findByUserIdOrderByOrderDateDesc(userId);
        
        return orders.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public OrderDTO createOrder(OrderRequest request, String userID) {

        Order order = new Order();
        order.setUserId(userID);
        order.setProducts(request.getProducts());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        repository.save(order);

        return mapToDTO(order);
    }

    public OrderDTO updateOrderStatus(String id, String status) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        repository.save(order);
        repository.save(order);

        return mapToDTO(order);
    }

    public void deleteOrder(String id) {

        Order order = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        repository.delete(order);
    }

    // DTO mapper
    private OrderDTO mapToDTO(Order order) {

        return OrderDTO.builder()
                .id(order.getId())
                .products(order.getProducts())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .build();
    }
}