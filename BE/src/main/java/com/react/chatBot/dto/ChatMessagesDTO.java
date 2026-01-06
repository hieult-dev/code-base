package com.react.chatBot.dto;

import com.react.chatBot.entity.MessageRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessagesDTO {
    private Long id;
    private MessageRole role;
    private String content;
    private Instant createdAt;
}
