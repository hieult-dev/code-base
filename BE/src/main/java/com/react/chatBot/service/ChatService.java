package com.react.chatBot.service;

import com.react.chatBot.dto.ChatResponse;
import com.react.chatBot.entity.ChatSession;
import com.react.chatBot.rag.service.RetrieverService;
import com.react.chatBot.repository.IChatMessageRepository;
import com.react.chatBot.repository.IChatSessionRepository;
import com.react.chatBot.entity.ChatMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ChatService {

    private final AIService aiService;
    private final RetrieverService retrieverService;
    private final IChatSessionRepository chatSessionRepo;
    private final IChatMessageRepository chatMessageRepo;

    public ChatService(
            AIService aiService,
            RetrieverService retrieverService,
            IChatSessionRepository chatSessionRepo,
            IChatMessageRepository chatMessageRepo
    ) {
        this.aiService = aiService;
        this.retrieverService = retrieverService;
        this.chatSessionRepo = chatSessionRepo;
        this.chatMessageRepo = chatMessageRepo;
    }

    @Transactional
    public ChatResponse chat(Long userId,String sessionId, String message) {

        String sid = (sessionId == null || sessionId.isBlank())
                ? UUID.randomUUID().toString()
                : sessionId;

        // 2) load/create session
        ChatSession session = chatSessionRepo.findBySessionKey(sid)
                .orElseGet(() -> {
                    ChatSession s = new ChatSession();
                    s.setSessionKey(sid);
                    s.setUserId(userId);
                    return chatSessionRepo.save(s);
                });

        // 3) lưu message user
        chatMessageRepo.save(ChatMessages.user(session, message));

        // 4) RAG context
        String ragContext = retrieverService.retrieveAsContext(message, 6);

        String answer;
        if (ragContext == null || ragContext.isBlank()) {
            answer = "Tôi không chắc";
        } else {
            answer = aiService.chat(message, ragContext);
        }

        // 5) lưu message assistant và lấy messageId
        ChatMessages botMsg = chatMessageRepo.save(ChatMessages.assistant(session, answer));

        return new ChatResponse(sid, botMsg.getId(), answer);
    }

    @Transactional
    public String createSession(Long userId) {
        String sid = UUID.randomUUID().toString();
        ChatSession s = new ChatSession();
        s.setSessionKey(sid);
        s.setUserId(userId);
        chatSessionRepo.save(s);
        return sid;
    }

}
