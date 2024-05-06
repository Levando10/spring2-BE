package com.example.backendglasses.controller;

import com.example.backendglasses.model.Message;
import com.example.backendglasses.model.Role;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.MessageDTO;
import com.example.backendglasses.service.impl.IAccountService;
import com.example.backendglasses.service.impl.IMessageService;
import com.example.backendglasses.service.impl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;


@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IMessageService iMessageService;

    @MessageMapping("/chat")
    public Message sendMessage(@Payload MessageDTO messageDTO){
        LocalDateTime time = LocalDateTime.now();
        messageDTO.setTimestamp(time);
        Message message = convertToMessage(messageDTO);
        messagingTemplate.convertAndSendToUser(messageDTO.getIdSender().toString(), "/private", message);
        return message;
    }


    @MessageMapping("/admin-chat")
    public void sendMessageToUser(@Payload MessageDTO messageDTO) {
        LocalDateTime time = LocalDateTime.now();
        messageDTO.setTimestamp(time);
        Message message = convertToMessage(messageDTO);

        messagingTemplate.convertAndSendToUser(
                messageDTO.getIdReceiver().toString(), "/AdminPrivate", message);
    }

    public Message convertToMessage(MessageDTO messageDTO) {
        Message message = new Message();


        User sender = iAccountService.findAccountByAccountIdAccount(messageDTO.getIdSender());

        User  receiver = null;
        if (sender.getRole().getName().equals("ADMIN")){
            System.out.println("zo zo ADMIN");
            receiver = iAccountService.findAccountByAccountIdAccount(messageDTO.getIdReceiver());
            message.setContent(messageDTO.getContent());
            message.setTimestamp(messageDTO.getTimestamp());
            message.setReceiver(receiver);
            message.setSender(sender);
        } else {
            System.out.println("ZO ZO USER GUI");
            Role  role = iRoleService.findRoleByName(messageDTO.getReceiver());
            receiver = iAccountService.findAccountByRole(role);
            message.setContent(messageDTO.getContent());
            message.setTimestamp(messageDTO.getTimestamp());
            message.setReceiver(receiver);
            message.setSender(sender);
        }


        return iMessageService.save(message);
    }
}
