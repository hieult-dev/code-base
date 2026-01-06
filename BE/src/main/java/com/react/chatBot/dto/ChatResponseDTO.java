package com.react.chatBot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatResponseDTO {
    private String sessionId;
    private Long messageId;
    private String answer;
}