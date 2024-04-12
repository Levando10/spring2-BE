//package com.example.backendglasses.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.handler.annotation.*;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.annotation.SendToUser;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    @Autowired
//    SimpMessagingTemplate messagingTemplate;
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic","/queue");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws-endpoint").setAllowedOrigins("http://localhost:3000").withSockJS();
//    }
//
//    @MessageMapping("/broadcast")
//    @SendTo("/topic/reply")
//    public String broadcastMessage(@Payload String message) {
//        return "You have received a message: " + message;
//    }
//
//    @MessageMapping("/user-message")
//    @SendToUser("/queue/reply")
//    public String sendBackToUser(@Payload String message, @Header("simpSessionId") String sessionId) {
//        return "Only you have received this message: " + message;
//    }
//    @MessageMapping("/user-message-{userName}")
//    public void sendToOtherUser(@Payload String message, @DestinationVariable String userName, @Header("simpSessionId") String sessionId) {
//        messagingTemplate.convertAndSend("/queue/reply-" + userName, "You have a message from someone: " + message);
//    }
//
//}
