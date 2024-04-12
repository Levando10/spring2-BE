package com.example.backendglasses.controller;

import com.example.backendglasses.model.Category;
import com.example.backendglasses.model.dto.ProductDTO;
import com.example.backendglasses.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryRESTController {
    @Autowired
    private ICategoryService iCategoryService;
    @GetMapping("")
    public ResponseEntity<Object> listCategory() {
        List<Category> list = iCategoryService.findAll();


        return new ResponseEntity<>( list, HttpStatus.OK);
    }


}
