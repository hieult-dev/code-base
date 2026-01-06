package com.react.chatBot.dto;

import com.react.chatBot.entity.MessageRole;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatMessageDTO {
    private Long id;
    private MessageRole role;
    private String content;
    private Instant createdAt;
}
