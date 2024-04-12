package com.example.backendglasses.service.impl;

import com.example.backendglasses.model.User;

import java.util.Optional;
import java.util.zip.DataFormatException;

public interface IAccountService {
    Optional<User> findAccountByEmail(String email);



    Optional<User> findAccountByAccountName(String accountName);

    User registerAccount(User user) throws Exception ;

    Optional<User> findAccountByPhone(String phoneNumber);
    String login(String nameAccount,String passWord) throws Exception;

    User findAccountByAccountIdAccount(Long idAccount);

    void save(User user);
}
