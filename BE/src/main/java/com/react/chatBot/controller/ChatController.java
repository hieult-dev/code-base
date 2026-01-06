package com.react.chatBot.controller;

import com.react.chatBot.dto.ChatMessageDTO;
import com.react.chatBot.dto.ChatRequestDTO;
import com.react.chatBot.dto.ChatResponseDTO;
import com.react.chatBot.dto.ChatSessionDTO;
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
    public ChatResponseDTO chat(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestBody ChatRequestDTO req
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
    public List<ChatSessionDTO> listSessions(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return chatService.findByUserIdOrderByUpdatedAtDesc(userId)
                .stream()
                .map(s -> new ChatSessionDTO(
                        s.getSessionKey(),
                        s.getUpdatedAt()
                ))
                .toList();
    }

    @GetMapping("/sessions/{sessionKey}/messages")
    public List<ChatMessageDTO> getMessages(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @PathVariable String sessionKey
    ) {
        return chatService.getMessagesBySessionKey(userId, sessionKey);
    }
}
