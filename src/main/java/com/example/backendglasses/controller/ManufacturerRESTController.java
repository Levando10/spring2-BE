package com.example.backendglasses.controller;

import com.example.backendglasses.model.Category;
import com.example.backendglasses.model.Manufacturer;
import com.example.backendglasses.service.impl.ICategoryService;
import com.example.backendglasses.service.impl.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/manufacturer")
public class ManufacturerRESTController {
    @Autowired
    private IManufacturerService iManufacturerService;


    @GetMapping("")
    public ResponseEntity<Object> listManufacturer() {
        List<Manufacturer> list = iManufacturerService.findAll();


        return new ResponseEntity<>( list, HttpStatus.OK);
    }
}
