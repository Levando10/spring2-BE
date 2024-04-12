package com.example.backendglasses.service.impl;

import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IShoppingCartService {
    ShoppingCart save(ShoppingCart shoppingCart);

    ShoppingCart findShoppingCartByUserAndStatus(User user, String unpaid);

    Page<CartDTO> historyCartDetail(Pageable pageable, Long idUser);
}
