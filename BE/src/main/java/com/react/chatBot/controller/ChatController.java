package com.react.chatBot.controller;

import com.react.chatBot.dto.ChatRequest;
import com.react.chatBot.dto.ChatResponse;
import com.react.chatBot.entity.ChatSession;
import com.react.chatBot.rag.service.RetrieverService;
import com.react.chatBot.service.AIService;
import com.react.chatBot.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestBody ChatRequest req
    ) {
        return chatService.chat(userId, req.getSessionId(), req.getMessage());
    }

    // create new conversation
    @PostMapping("/sessions")
    public Map<String, String> createSession(
            @RequestHeader(value = "X-User-Id", required = false) Long userId
    ) {
        String sessionId = chatService.createSession(userId);
        return Map.of("sessionId", sessionId);
    }



}
