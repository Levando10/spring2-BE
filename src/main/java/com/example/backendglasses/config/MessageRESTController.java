package com.example.backendglasses.config;

import com.example.backendglasses.model.Message;
import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.User;
import com.example.backendglasses.service.impl.IAccountService;
import com.example.backendglasses.service.impl.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/message")
public class MessageRESTController {
    @Autowired
    private IMessageService iMessageService;
    @Autowired
    private IAccountService iAccountService;

    @GetMapping("/")
    public ResponseEntity<Object> listMessageByUser(@RequestParam(value = "idAccount", required = false) Long id) {
        List<Message> list = iMessageService.listMessageByUser(id);


        return new ResponseEntity<>( list, HttpStatus.OK);
    }

}
