package com.example.backendglasses.model.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageDTO {
    private String content;
    private String receiver;
    private String username;
    private Long idSender;
    private Long idReceiver;
    private LocalDateTime timestamp;
}
