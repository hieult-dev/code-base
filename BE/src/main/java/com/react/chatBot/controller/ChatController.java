package com.react.chatBot.controller;

import com.react.chatBot.dto.ChatRequest;
import com.react.chatBot.dto.ChatResponse;
import com.react.chatBot.dto.ChatSession;
import com.react.chatBot.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // fe kh gửi lại SessionId thì là conversation mới
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

    // list các conversation
    @GetMapping("/sessions")
    public List<ChatSession> listSessions(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return chatService.findByUserIdOrderByUpdatedAtDesc(userId)
                .stream()
                .map(s -> new ChatSession(
                        s.getSessionKey(),
                        s.getUpdatedAt()
                ))
                .toList();
    }
}
