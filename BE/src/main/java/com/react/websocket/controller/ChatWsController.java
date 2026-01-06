package com.react.websocket.controller;

import com.react.chatBot.service.ChatService;
import com.react.websocket.dto.ChatWsRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWsController {
    private final ChatService chatService;

    public ChatWsController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
    public void send(ChatWsRequest req) {
        chatService.chatWs(req.getUserId(), req.getSessionId(), req.getMessage());
    }
}
