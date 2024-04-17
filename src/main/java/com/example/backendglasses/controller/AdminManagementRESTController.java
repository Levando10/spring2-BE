package com.example.backendglasses.controller;

import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.ApiResponse;
import com.example.backendglasses.model.dto.CartDTO;
import com.example.backendglasses.model.dto.ProductDTO;
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
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("admin")
public class AdminManagementRESTController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private IShoppingCartItemService iShoppingCartItemService;

    @GetMapping("/cart")
    public ResponseEntity<Object> managementHistoryCartOrder( @PageableDefault(size = 5) Pageable pageable){
        Page<CartDTO> cartDTOS = iShoppingCartService.managementHistoryCartOrder(pageable);


        return new ResponseEntity<>( cartDTOS, HttpStatus.OK);

    }
    @GetMapping("/searchCart")
    public ResponseEntity<Object> searchHistoryCartOrder( @PageableDefault(size = 5) Pageable pageable,
                                                          @RequestParam(value = "searchDate", required = false ) LocalDate searchDate,
                                                          @RequestParam(value = "searchName", required = false ) String searchName) {
        LocalDateTime searchDateTime = null;
        Page<CartDTO> cartDTOS = iShoppingCartService.managementHistoryCartOrder(pageable);
        if (searchDate != null) {
            searchDateTime = searchDate.atStartOfDay();
        }
        if (searchDate == null && searchName == null){
            return new ResponseEntity<>( cartDTOS, HttpStatus.OK);
        } else if (searchDate != null && searchName == ""){
            cartDTOS = iShoppingCartService.searchCartHistoryWithDate(pageable,searchDateTime);
        } else if (searchDate != null && searchName != "") {
            cartDTOS = iShoppingCartService.searchCartHistoryWithDateAndName(pageable,searchDateTime,searchName);
        } else if (searchDate == null && searchName != "") {
            cartDTOS = iShoppingCartService.searchCartHistoryWithName(pageable,searchName);
        }

        return new ResponseEntity<>( cartDTOS, HttpStatus.OK);
    }

    @GetMapping("/managementProduct")
    public ResponseEntity<Object> managementProduct( @PageableDefault(size = 5) Pageable pageable){
        Page<ProductDTO> productDTOS = iProductService.findAllProductDTOManagement(pageable);

        return new ResponseEntity<>( productDTOS, HttpStatus.OK);

    }
    @GetMapping("/searchProduct")
    public ResponseEntity<Object> managementSearchProduct( @PageableDefault(size = 5) Pageable pageable,
                                                           @RequestParam(value = "searchName", required = false ) String searchName){
        Page<ProductDTO> productDTOS = iProductService.findAllProductDTOManagement(pageable);
        if (searchName == ""){
            System.out.println(searchName);
            return new ResponseEntity<>( productDTOS, HttpStatus.OK);
        } else{
            System.out.println(searchName);
            productDTOS = iProductService.searchProductDTOManagement(pageable,searchName);
            return new ResponseEntity<>( productDTOS, HttpStatus.OK);
        }

    }


}
