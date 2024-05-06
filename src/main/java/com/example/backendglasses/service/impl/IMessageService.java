package com.example.backendglasses.service.impl;

import com.example.backendglasses.model.Message;
import com.example.backendglasses.model.User;

import java.util.List;

public interface IMessageService {
    List<Message> listMessageByUser(Long idAccount);

    Message save(Message message);
}
