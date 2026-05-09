package com.cg.genzstore.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.cg.genzstore.model.entity.CartItem;

@Getter
@Setter
@Builder
public class CartDTO {

    private String id;

    private List<CartItem> items;
}