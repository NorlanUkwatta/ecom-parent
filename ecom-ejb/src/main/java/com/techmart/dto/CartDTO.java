package com.techmart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO implements Serializable {
    private Long id;
    private List<CartItemDTO> items = new ArrayList<>();
}