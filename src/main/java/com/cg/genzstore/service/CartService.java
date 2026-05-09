package com.cg.genzstore.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.genzstore.model.dto.CartDTO;
import com.cg.genzstore.model.entity.Cart;
import com.cg.genzstore.model.entity.CartItem;
import com.cg.genzstore.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartDTO getCartDTO(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>()));

        return CartDTO.builder()
                .id(cart.getId())
                .items(cart.getItems())
                .build();
    }

    private Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>()));
    }

    public CartDTO addToCart(String userId, CartItem item) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(null, userId, new ArrayList<>()));

        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(i -> i.getProduct().getId().equals(item.getProduct().getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + item.getQuantity());
        } else {
            cart.getItems().add(item);
        }
        cartRepository.save(cart);

        return CartDTO.builder()
                .id(cart.getId())
                .items(cart.getItems())
                .build();
    }

    public CartDTO decreaseItem(String userId, CartItem item) {

        Cart cart = getCart(userId);

        for (CartItem i : cart.getItems()) {
            if (i.getProduct().getId().equals(item.getProduct().getId())) {
                i.setQuantity(i.getQuantity() - 1);
            }
        }

        // remove items with qty <= 0
        cart.setItems(
                cart.getItems()
                        .stream()
                        .filter(i -> i.getQuantity() > 0)
                        .toList());

        cartRepository.save(cart);

        return CartDTO.builder()
                .id(cart.getId())
                .items(cart.getItems())
                .build();
    }

    public CartDTO removeItem(String userId, String productId) {

        Cart cart = getCart(userId);

        cart.setItems(
                cart.getItems()
                        .stream()
                        .filter(i -> !i.getProduct().getId().equals(productId))
                        .toList());

        cartRepository.save(cart);

        return CartDTO.builder()
                .id(cart.getId())
                .items(cart.getItems())
                .build();
    }

    public void clearCart(String userId) {

        Cart cart = getCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}