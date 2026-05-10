package com.cg.genzstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.genzstore.model.dto.OrderDTO;
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
    public List<OrderDTO> getCart(@RequestHeader("Authorization") String header) {
        return orderService.getOrdersByUser(userService.getUserId(header));
    }
}
