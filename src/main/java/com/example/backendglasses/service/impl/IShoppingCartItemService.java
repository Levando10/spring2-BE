package com.example.backendglasses.service.impl;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.ShoppingCartItem;
import com.example.backendglasses.model.dto.ShoppingCartItemDTO;

import java.util.List;

public interface IShoppingCartItemService {

    ShoppingCartItem save(ShoppingCartItem shoppingCartItem);

    Integer countShoppingCartItemByShoppingCart(ShoppingCart shoppingCart);

    void deleteProductInCart( Long idProduct, Long id);

    void decreaseProduct(Long idProduct, Long idCart);

    List<ShoppingCartItemDTO> findShoppingCartItemDetail(Long idCart);
}
