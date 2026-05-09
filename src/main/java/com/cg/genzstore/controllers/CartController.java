package com.cg.genzstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.genzstore.model.dto.CartDTO;
import com.cg.genzstore.model.entity.CartItem;
import com.cg.genzstore.service.CartService;
import com.cg.genzstore.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public CartDTO getCart(@RequestHeader("Authorization") String header) {
        return cartService.getCartDTO(userService.getUserId(header));
    }

    @PostMapping("/add")
    public CartDTO addToCart(@RequestHeader("Authorization") String header,
            @RequestBody CartItem item) {
        return cartService.addToCart(userService.getUserId(header), item);
    }

    @PutMapping("/decrease")
    public CartDTO decreaseItem(@RequestHeader("Authorization") String header,
            @RequestBody CartItem item) {
        return cartService.decreaseItem(userService.getUserId(header), item);
    }

    @DeleteMapping("/item/{productId}")
    public CartDTO removeItem(@RequestHeader("Authorization") String header,
            @PathVariable String productId) {
        return cartService.removeItem(userService.getUserId(header), productId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@RequestHeader("Authorization") String header) {
        cartService.clearCart(userService.getUserId(header));
    }
}