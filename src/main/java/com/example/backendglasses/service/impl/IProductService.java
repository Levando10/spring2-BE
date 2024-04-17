package com.example.backendglasses.service.impl;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProductService {
//    Page<ProductDTO> findAllProduct(Pageable pageable);
    Page<Product> findAllProduct(Pageable pageable);
    Page<ProductDTO> findAllProductDTO(Pageable pageable);

    ProductDTO findProductById(Long id);

    Product findById(Long id);

    void removeProduct(Product product);

    List<ProductDTO> detailCartIndividual(Long idCart);

    Page<ProductDTO> searchWithParamCan(Pageable pageable, String canSearch);

    Page<ProductDTO> searchWithParamMat(Pageable pageable, String searchMat);

    Page<ProductDTO> searchWithParamCanAndMatAndGucci(Pageable pageable, String searchCan, String searchMat, String searchGucci);

    Page<ProductDTO> searchWithParamCanAndMatAndDior(Pageable pageable, String searchCan, String searchMat, String searchDior);

    Page<ProductDTO> searchWithParamCanAndGuuci(Pageable pageable, String searchCan, String searchGucci);

    Page<ProductDTO> searchWithParamCanAndDior(Pageable pageable, String searchCan, String searchDior);

    Page<ProductDTO> searchWithParamCanAndDiorAndGuuci(Pageable pageable, String searchCan, String searchGucci, String searchDior);

    Page<ProductDTO> searchWithParamMatAndGuuci(Pageable pageable, String searchMat, String searchGucci);

    Page<ProductDTO> searchWithParamMatAndDior(Pageable pageable, String searchMat, String searchDior);

    Page<ProductDTO> searchWithParamMatAndDiorAndGuuci(Pageable pageable, String searchMat, String searchGucci, String searchDior);

    Page<ProductDTO> searchWithParamGucci(Pageable pageable, String searchGucci);

    Page<ProductDTO> searchWithParamDior(Pageable pageable, String searchDior);

    Page<ProductDTO> searchWithParamGucciAndDior(Pageable pageable, String searchGucci, String searchDior);

    void save(Product product);

    Page<Product> findAllByQuantityGreaterThan(Pageable pageable, Integer  integer);

    Page<ProductDTO> searchProductDTOManagement(Pageable pageable, String searchName);

    Page<ProductDTO> findAllProductDTOManagement(Pageable pageable);
}
