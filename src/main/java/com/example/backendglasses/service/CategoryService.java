package com.example.backendglasses.service;

import com.example.backendglasses.model.Category;
import com.example.backendglasses.repository.CategoryRepository;
import com.example.backendglasses.service.impl.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
