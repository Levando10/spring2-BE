package com.example.backendglasses.service;

import com.example.backendglasses.model.Role;
import com.example.backendglasses.repository.RoleRepository;
import com.example.backendglasses.service.impl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleByName(String receiver) {
        return roleRepository.findByName(receiver);
    }
}
