package com.example.backendglasses.service;

import com.example.backendglasses.model.Message;
import com.example.backendglasses.model.User;
import com.example.backendglasses.repository.MessageRepository;
import com.example.backendglasses.service.impl.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> listMessageByUser(Long idAccount  ) {
        return messageRepository.getAllBySender(idAccount);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
