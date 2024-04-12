package com.example.backendglasses.controller;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.ShoppingCartItem;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.*;
import com.example.backendglasses.service.impl.IProductService;
import com.example.backendglasses.service.impl.IShoppingCartItemService;
import com.example.backendglasses.service.impl.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/cart")
public class CartRESTController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private IShoppingCartItemService iShoppingCartItemService;

    @GetMapping("/")
    public ResponseEntity<Object> detailCartIndividual(@RequestParam(value = "idUser", required = false) Long idUser){
        User user = new User(idUser);
        ShoppingCart cart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        List<ProductDTO> list = iProductService.detailCartIndividual(cart.getId());
        System.out.println(list.size());
        System.out.println(list.size());
        response.setDataRes(list);

        Double sum = 0D;
        for (ProductDTO temp : list){
            sum += temp.getPrice() * temp.getQuantity();
        }
        System.out.println(sum);
        response.setTotalPrice(sum);

            return new ResponseEntity<>( response,HttpStatus.OK);


    }


    @DeleteMapping("/")
    public ResponseEntity<Object> deleteProductInCart(@RequestParam(value = "idUser", required = false) Long idUser, @RequestParam(value = "idProduct",required = false) Long idProduct){
        User user = new User(idUser);
        ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
        ApiResponse<List<ProductDTO>> response = new ApiResponse<>();
        List<ProductDTO> list = iProductService.detailCartIndividual(shoppingCart.getId());
        response.setDataRes(list);
        Double sum = 0D;
        for (ProductDTO temp : list){
            sum += temp.getPrice() * temp.getQuantity();
        }
        response.setTotalPrice(sum);
        if (shoppingCart != null){
            iShoppingCartItemService.deleteProductInCart(idProduct,shoppingCart.getId());

        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @PostMapping("/addToCartHome")
    public ResponseEntity<Object> checkProductExistInCart(@RequestBody AccountDTO accountDTO){
        User user = new User(accountDTO.getIdAccount());
        Product product = new Product(accountDTO.getIdProduct());
//        ProductDTO productItem = iProductService.findProductById(accountDTO.getIdProduct());
        Product product1 = iProductService.findById(product.getId());


        if (product1 != null){
            ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
            if (shoppingCart != null){
                iShoppingCartItemService.save(new ShoppingCartItem(shoppingCart,product,product1.getPrice(),1));

                return new ResponseEntity<>( "OK",HttpStatus.OK);
            } else {
                ShoppingCart newShoppingCart = iShoppingCartService.save(new ShoppingCart(user,"unpaid"));
                iShoppingCartItemService.save(new ShoppingCartItem(newShoppingCart,product,product1.getPrice(),1));

            }
        } else {
            return new ResponseEntity<>( "BAD_REQUEST",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>( "OK",HttpStatus.OK);
    }

    @PostMapping("/decreaseProductInCart")
    public ResponseEntity<Object> decreaseProductInCart(@RequestBody AccountDTO accountDTO){
        User user = new User(accountDTO.getIdAccount());
        ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");

        iShoppingCartItemService.decreaseProduct(accountDTO.getIdProduct(),shoppingCart.getId());



        return new ResponseEntity<>( "OK",HttpStatus.OK);
    }

    @GetMapping("/qualityProductInCart")
    public ResponseEntity<Object> qualityProductInCart(@RequestParam(value = "idUser", required = false) Long idUser){
        User user = new User(idUser);
        ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
        if (shoppingCart != null){
            Integer quality = iShoppingCartItemService.countShoppingCartItemByShoppingCart(shoppingCart);
            return new ResponseEntity<>( quality,HttpStatus.OK);

        } else {
            return new ResponseEntity<>( "NO_CONTENT",HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/historyCart")
    public ResponseEntity<Object> historyCartDetail(@RequestParam("idUser") Long idUser,
                                                    @PageableDefault(size = 5) Pageable pageable){
        Page<CartDTO> cartDTOS = iShoppingCartService.historyCartDetail(pageable,idUser);

        
        return new ResponseEntity<>( cartDTOS,HttpStatus.OK);
    }

    @GetMapping("/detailCartItem")
    public ResponseEntity<Object> detailCartBooking(@RequestParam("idCart") Long idCart) {

        List<ShoppingCartItemDTO> bookingDetail = iShoppingCartItemService.findShoppingCartItemDetail(idCart);


        return new ResponseEntity<>( bookingDetail,HttpStatus.OK);
    }


}
