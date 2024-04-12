package com.example.backendglasses.repository;

import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.ShoppingCartItem;
import com.example.backendglasses.model.dto.ShoppingCartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Long> {

     Integer countShoppingCartItemByShoppingCart(ShoppingCart shoppingCart);

    @Modifying
    @Transactional
     @Query(value = "delete from shopping_cart_item " +
             "where shopping_cart_item.cart_id = :idCart and shopping_cart_item.product_id = :idProduct",nativeQuery = true)
    void deleteProductInCartUser(@Param("idProduct") Long idProduct,@Param("idCart") Long idCart);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM shopping_cart_item WHERE product_id = :idProduct and cart_id = :idCart LIMIT 1;", nativeQuery = true)
    void decreaseProduct(@Param("idProduct") Long idProduct, @Param("idCart") Long idCart);


    @Query(value = "SELECT SUM(shopping_cart_item.quantity) as quantity, \n" +
            "       product.name as nameProduct,\n" +
            "       shopping_cart_item.unit_price as priceUnit, \n" +
            "       shopping_cart.address_ship as address,\n" +
            "       (SELECT url_image  FROM image_product \n" +
            "       WHERE product_image = shopping_cart_item.product_id LIMIT 1) as imageMax\n" +
            "FROM shopping_cart_item\n" +
            " JOIN product ON product.id = shopping_cart_item.product_id\n" +
            " JOIN shopping_cart ON shopping_cart.id = shopping_cart_item.cart_id\n" +
            "WHERE shopping_cart_item.cart_id = :idCart\n" +
            "GROUP BY shopping_cart_item.product_id,\n" +
            "         shopping_cart_item.unit_price,\n" +
            "         product.name,\n" +
            "         shopping_cart.address_ship", nativeQuery = true)
    List<ShoppingCartItemDTO> findShoppingCartItemDetail(Long idCart);



}
