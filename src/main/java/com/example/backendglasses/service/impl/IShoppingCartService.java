package com.example.backendglasses.service.impl;

import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IShoppingCartService {
    ShoppingCart save(ShoppingCart shoppingCart);

    ShoppingCart findShoppingCartByUserAndStatus(User user, String unpaid);

    Page<CartDTO> historyCartDetail(Pageable pageable, Long idUser);

    Page<CartDTO> managementHistoryCartOrder(Pageable pageable);

    Page<CartDTO> searchCartHistoryWithDate(Pageable pageable, LocalDateTime searchDateTime);

    Page<CartDTO> searchCartHistoryWithDateAndName(Pageable pageable, LocalDateTime searchDateTime, String searchName);

    Page<CartDTO> searchCartHistoryWithName(Pageable pageable, String searchName);
}
