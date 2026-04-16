package com.cg.genzstore.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.genzstore.model.entity.Cart;
import com.cg.genzstore.model.entity.CartItem;
import com.cg.genzstore.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>()));
    }

    public Cart addToCart(String userId, CartItem item) {
        Cart cart = getCart(userId);

        cart.getItems().add(item);

        return cartRepository.save(cart);
    }
}