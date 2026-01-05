package com.react.chatBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatSessionDTO {
    private String sessionId;
    private LocalDateTime updatedAt;
}
