package com.example.backendglasses.controller;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.dto.ProductDTO;
import com.example.backendglasses.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductRESTController {

    @Autowired
    private IProductService iProductService;

//    @GetMapping("")
//    public ResponseEntity<Object> listProduct(@PageableDefault(size = 6) Pageable pageable) {
//        Page<ProductDTO> productDTOS = iProductService.findAllProduct(pageable);
//
//        return new ResponseEntity<>( productDTOS,HttpStatus.OK);
//    }
@GetMapping("")
public ResponseEntity<Object> listProduct(@PageableDefault(size = 6) Pageable pageable) {
    Page<Product> products = iProductService.findAllByQuantityGreaterThan(pageable,0);

    return new ResponseEntity<>( products,HttpStatus.OK);
}

    @GetMapping("/listFull")
    public ResponseEntity<Object> listProductFull(@PageableDefault(size = 6) Pageable pageable) {
        Page<ProductDTO> products = iProductService.findAllProductDTO(pageable);

        return new ResponseEntity<>( products,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> detailProduct(@RequestParam(value = "idProduct", required = false) Long id) {
//        ProductDTO productDTO = iProductService.findProductById(id);
        Product product = iProductService.findById(id);
        if (product != null){
            return new ResponseEntity<>( product,HttpStatus.OK);
        }
        return new ResponseEntity<>( "BAD_REQUEST",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchWithCan(@PageableDefault(size = 6) Pageable pageable,
                                                @RequestParam(value = "canSearch", required = false ) String searchCan,
                                                @RequestParam(value = "searchMat", required = false) String searchMat,
                                                @RequestParam(value = "searchGucci", required = false) String searchGucci,
                                                @RequestParam(value = "searchDior", required = false) String searchDior) {

        Page<ProductDTO> products = null;
        if (searchCan == "" && searchMat == "" && searchGucci == "" && searchDior == ""){
            products = iProductService.findAllProductDTO(pageable);

        } else if (searchCan != "" && searchMat != "" && searchGucci == "" && searchDior == "") {
            products = iProductService.findAllProductDTO(pageable);
        } else if (searchCan != "" && searchMat != "" && searchGucci != "" && searchDior == "" ) {
            products = iProductService.searchWithParamCanAndMatAndGucci(pageable,searchCan,searchMat,searchGucci);
        } else if (searchCan != "" && searchMat != "" && searchGucci == "" && searchDior != "" ) {
            products = iProductService.searchWithParamCanAndMatAndDior(pageable,searchCan,searchMat,searchDior);
        } else if (searchCan != "" && searchMat == "" && searchGucci != "" && searchDior == "" ) {
            products = iProductService.searchWithParamCanAndGuuci(pageable,searchCan,searchGucci);
        } else if (searchCan != "" && searchMat == "" && searchGucci == "" && searchDior != "") {
            products = iProductService.searchWithParamCanAndDior(pageable,searchCan,searchDior);
        } else if (searchCan != "" && searchMat == "" && searchGucci != "" && searchDior != "") {
            products = iProductService.searchWithParamCanAndDiorAndGuuci(pageable,searchCan,searchGucci,searchDior);
        }
        else if (searchCan == "" && searchMat != "" && searchGucci != "" && searchDior == "") {
            products = iProductService.searchWithParamMatAndGuuci(pageable,searchMat,searchGucci);
        } else if (searchCan == "" && searchMat != "" && searchGucci == "" && searchDior != "") {
            products = iProductService.searchWithParamMatAndDior(pageable,searchMat,searchDior);
        }  else if (searchCan == "" && searchMat != "" && searchGucci != "" && searchDior != "") {
        products = iProductService.searchWithParamMatAndDiorAndGuuci(pageable,searchMat,searchGucci,searchDior);
    }
        else if (searchCan == "" && searchMat == "" && searchGucci != "" && searchDior == "") {
            products = iProductService.searchWithParamGucci(pageable,searchGucci);
        }
        else if (searchCan == "" && searchMat == "" && searchGucci == "" && searchDior != "") {
            products = iProductService.searchWithParamDior(pageable,searchDior);
        }
        else if (searchCan == "" && searchMat == "" && searchGucci != "" && searchDior != "") {
            products = iProductService.searchWithParamGucciAndDior(pageable,searchGucci,searchDior);
        } else if (searchCan != "" && searchMat != "" && searchGucci != "" && searchDior != "") {
            products = iProductService.findAllProductDTO(pageable);
        }


        if (searchCan != "" && searchMat == "" && searchGucci == "" && searchDior == ""){
           products = iProductService.searchWithParamCan(pageable,searchCan);
       } else if (searchCan == "" && searchMat != "" && searchGucci == "" && searchDior == "") {
            products = iProductService.searchWithParamMat(pageable,searchMat);
        }

//for (ProductDTO dto : products){
//    System.out.println(dto.getImageMax());
//}

        return new ResponseEntity<>( products,HttpStatus.OK);
    }



    @DeleteMapping("/")
    public ResponseEntity<Object> deleteProduct(@RequestParam(value = "idProduct", required = false) Long id) {
        Product product  = iProductService.findById(id);
        if (product != null){
            iProductService.removeProduct(product);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>( "BAD_REQUEST",HttpStatus.BAD_REQUEST);
    }


}
