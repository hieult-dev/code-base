package com.react.websocket.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ChatWsRequest {
    private Long userId;
    private String sessionId;
    private String message;
}
