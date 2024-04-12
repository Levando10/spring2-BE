package com.example.backendglasses.service;

import com.example.backendglasses.model.Manufacturer;
import com.example.backendglasses.repository.ManufacturerRepository;
import com.example.backendglasses.service.impl.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService implements IManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }
}
