package com.example.backendglasses.repository;

import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

    ShoppingCart findShoppingCartByUserAndStatus(User user, String status);

    @Query(value = "select   shopping_cart.id as idCart ,shopping_cart.status as statusCart, sum(shopping_cart_item.unit_price) as totalPrice , shopping_cart.order_date as orderDay,\n" +
            "shopping_cart.address_ship as addressShip\n"+
            "from shopping_cart\n" +
            "join shopping_cart_item on shopping_cart_item.cart_id = shopping_cart.id\n" +
            "where shopping_cart.status = \"paid\" and shopping_cart.user_id = :idUser\n" +
            "group by shopping_cart_item.cart_id\n" +
            "order by shopping_cart.order_date  desc ", nativeQuery = true)
    Page<CartDTO> historyCart(Pageable pageable, Long idUser);

    @Query(value = "select   shopping_cart.id as idCart ,shopping_cart.status as statusCart, sum(shopping_cart_item.unit_price) as totalPrice , shopping_cart.order_date as orderDay,\n" +
            "shopping_cart.address_ship as addressShip\n, user.full_name as fullName\n"+
            "from shopping_cart\n" +
            "join shopping_cart_item on shopping_cart_item.cart_id = shopping_cart.id\n"+
            "join user on user.id = shopping_cart.user_id\n"+
            "where shopping_cart.status = \"paid\"\n" +
            "group by shopping_cart_item.cart_id\n" +
            "order by shopping_cart.order_date  desc,totalPrice desc\n", nativeQuery = true)
    Page<CartDTO> managementHistoryCartOrder(Pageable pageable);

    @Query(value = "select   shopping_cart.id as idCart ,shopping_cart.status as statusCart, sum(shopping_cart_item.unit_price) as totalPrice , shopping_cart.order_date as orderDay,\n" +
            "shopping_cart.address_ship as addressShip\n, user.full_name as fullName\n"+
            "from shopping_cart\n" +
            "join shopping_cart_item on shopping_cart_item.cart_id = shopping_cart.id\n"+
            "join user on user.id = shopping_cart.user_id\n"+
            "where shopping_cart.status = \"paid\" and shopping_cart.order_date >= :searchDateTime\n" +
            "group by shopping_cart_item.cart_id\n" +
            "order by shopping_cart.order_date  desc ", nativeQuery = true)
    Page<CartDTO> searchCartHistoryWithDate(Pageable pageable, LocalDateTime searchDateTime);

    @Query(value = "select   shopping_cart.id as idCart ,shopping_cart.status as statusCart, sum(shopping_cart_item.unit_price) as totalPrice , shopping_cart.order_date as orderDay,\n" +
            "shopping_cart.address_ship as addressShip\n, user.full_name as fullName\n"+
            "from shopping_cart\n" +
            "join shopping_cart_item on shopping_cart_item.cart_id = shopping_cart.id\n"+
            "join user on user.id = shopping_cart.user_id\n"+
            "where (shopping_cart.status = \"paid\" and shopping_cart.order_date >= :searchDateTime) and user.full_name like %:searchName%\n" +
            "group by shopping_cart_item.cart_id\n" +
            "order by shopping_cart.order_date  desc ", nativeQuery = true)
    Page<CartDTO> searchCartHistoryWithDateAndName(Pageable pageable, LocalDateTime searchDateTime, String searchName);

    @Query(value = "select   shopping_cart.id as idCart ,shopping_cart.status as statusCart, sum(shopping_cart_item.unit_price) as totalPrice , shopping_cart.order_date as orderDay,\n" +
            "shopping_cart.address_ship as addressShip\n, user.full_name as fullName\n"+
            "from shopping_cart\n" +
            "join shopping_cart_item on shopping_cart_item.cart_id = shopping_cart.id\n"+
            "join user on user.id = shopping_cart.user_id\n"+
            "where (shopping_cart.status = \"paid\" and user.full_name like %:searchName%)\n" +
            "group by shopping_cart_item.cart_id\n" +
            "order by shopping_cart.order_date  desc ", nativeQuery = true)
    Page<CartDTO> searchCartHistoryWithName(Pageable pageable, String searchName);
}
