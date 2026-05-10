package com.cg.genzstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.genzstore.model.dto.OrderDTO;
import com.cg.genzstore.model.dto.OrderRequest;
import com.cg.genzstore.service.OrderService;
import com.cg.genzstore.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public List<OrderDTO> getOrders(@RequestHeader("Authorization") String header) {

        String userId = userService.getUserId(header);

        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable String id) {

        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(
            @RequestHeader("Authorization") String header,
            @RequestBody OrderRequest order) {

        String userId = userService.getUserId(header);

        return orderService.createOrder(order, userId);
    }

    @PutMapping("/{id}/status")
    public OrderDTO updateOrderStatus(
            @PathVariable String id,
            @RequestParam String status) {

        return orderService.updateOrderStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable String id) {

        orderService.deleteOrder(id);

        return "Order deleted successfully";
    }
}
