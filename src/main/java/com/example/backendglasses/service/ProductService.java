package com.example.backendglasses.service;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.dto.ProductDTO;
import com.example.backendglasses.repository.ProductRepository;
import com.example.backendglasses.service.impl.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

//    @Override
//    public Page<ProductDTO> findAllProduct(Pageable pageable) {
//        return productRepository.findAllProduct(pageable);
//    }


    @Override
    public Page<Product> findAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductDTO> findAllProductDTO(Pageable pageable) {
        return productRepository.findAllProductDTO(pageable);
    }

    @Override
    public ProductDTO findProductById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void removeProduct(Product product) {
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> detailCartIndividual(Long idCart) {
        return productRepository.detailCartIndividual(idCart);
    }

    @Override
    public Page<ProductDTO> searchWithParamCan(Pageable pageable, String canSearch) {
        return productRepository.searchWithParamCan(pageable,canSearch);
    }

    @Override
    public Page<ProductDTO> searchWithParamMat(Pageable pageable, String searchMat) {
        return productRepository.searchWithParamMat(pageable,searchMat);
    }

    @Override
    public Page<ProductDTO> searchWithParamCanAndMatAndGucci(Pageable pageable, String searchCan, String searchMat, String searchGucci) {
        return productRepository.searchWithParamCanAndMatAndGucci(pageable,searchCan,searchMat,searchGucci);
    }

    @Override
    public Page<ProductDTO> searchWithParamCanAndMatAndDior(Pageable pageable, String searchCan, String searchMat, String searchDior) {
        return productRepository.searchWithParamCanAndMatAndDior(pageable,searchCan,searchMat,searchDior);
    }

    @Override
    public Page<ProductDTO> searchWithParamCanAndGuuci(Pageable pageable, String searchCan, String searchGucci) {
        return productRepository.searchWithParamCanAndGuuci(pageable, searchCan , searchGucci);
    }

    @Override
    public Page<ProductDTO> searchWithParamCanAndDior(Pageable pageable, String searchCan, String searchDior) {
        return productRepository.searchWithParamCanAndDior(pageable, searchCan , searchDior);
    }

    @Override
    public Page<ProductDTO> searchWithParamCanAndDiorAndGuuci(Pageable pageable, String searchCan, String searchGucci, String searchDior) {
        return productRepository.searchWithParamCanAndDiorAndGuuci(pageable,searchCan,searchGucci,searchDior);
    }

    @Override
    public Page<ProductDTO> searchWithParamMatAndGuuci(Pageable pageable, String searchMat, String searchGucci) {
        return productRepository.searchWithParamMatAndGuuci(pageable,searchMat,searchGucci);
    }

    @Override
    public Page<ProductDTO> searchWithParamMatAndDior(Pageable pageable, String searchMat, String searchDior) {
        return productRepository.searchWithParamMatAndDior(pageable,searchMat,searchDior);
    }

    @Override
    public Page<ProductDTO> searchWithParamMatAndDiorAndGuuci(Pageable pageable, String searchMat, String searchGucci, String searchDior) {
        return productRepository.searchWithParamMatAndDiorAndGuuci(pageable,searchMat,searchGucci,searchDior);
    }

    @Override
    public Page<ProductDTO> searchWithParamGucci(Pageable pageable, String searchGucci) {
        return productRepository.searchWithParamGucci(pageable,searchGucci);
    }

    @Override
    public Page<ProductDTO> searchWithParamDior(Pageable pageable, String searchDior) {
        return productRepository.searchWithParamDior(pageable,searchDior);
    }

    @Override
    public Page<ProductDTO> searchWithParamGucciAndDior(Pageable pageable, String searchGucci, String searchDior) {
        return productRepository.searchWithParamGucciAndDior(pageable,searchGucci,searchDior);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }



    @Override
    public Page<Product> findAllByQuantityGreaterThan(Pageable pageable, Integer i) {
        return productRepository.findAllByQuantityGreaterThanAndIsDeletedFalse(pageable,i);
    }
}
