package com.example.backendglasses.service;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.ShoppingCartItem;
import com.example.backendglasses.model.dto.ShoppingCartItemDTO;
import com.example.backendglasses.repository.ShoppingCartItemRepository;
import com.example.backendglasses.service.impl.IShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartItemService implements IShoppingCartItemService {
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;


    @Override
    public ShoppingCartItem save(ShoppingCartItem shoppingCartItem) {
        return shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public Integer countShoppingCartItemByShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartItemRepository.countShoppingCartItemByShoppingCart(shoppingCart);
    }

    @Override
    public void deleteProductInCart(Long idProduct, Long idCart) {
        shoppingCartItemRepository.deleteProductInCartUser(idProduct,idCart);
    }

    @Override
    public void decreaseProduct(Long idProduct, Long idCart) {
        shoppingCartItemRepository.decreaseProduct(idProduct,idCart);
    }

    @Override
    public List<ShoppingCartItemDTO> findShoppingCartItemDetail(Long idCart) {
        return shoppingCartItemRepository.findShoppingCartItemDetail(idCart);
    }


}
