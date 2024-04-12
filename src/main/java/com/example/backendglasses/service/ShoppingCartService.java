package com.example.backendglasses.service;

import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.CartDTO;
import com.example.backendglasses.repository.ShoppingCartRepository;
import com.example.backendglasses.service.impl.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService implements IShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public  ShoppingCart save(ShoppingCart shoppingCart) {
       return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart findShoppingCartByUserAndStatus(User user, String unpaid) {
        return shoppingCartRepository.findShoppingCartByUserAndStatus(user,unpaid);
    }

    @Override
    public Page<CartDTO> historyCartDetail(Pageable pageable, Long idUser) {
        return shoppingCartRepository.historyCart(pageable,idUser);
    }
}
