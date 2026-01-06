package com.react.websocket.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatWsResponse {
    private String type; // STATUS | ASSISTANT_MESSAGE | ERROR
    private String sessionId;
    private String content;
    private Long messageId;

}
