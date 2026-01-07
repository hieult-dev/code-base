package com.react.chatBot.service;

import com.react.chatBot.dto.ChatMessageDTO;
import com.react.chatBot.dto.ChatResponseDTO;
import com.react.chatBot.entity.ChatSession;
import com.react.chatBot.entity.MessageRole;
import com.react.chatBot.exception.ConversationNotFound;
import com.react.chatBot.rag.service.RetrieverService;
import com.react.chatBot.repository.IChatMessageRepository;
import com.react.chatBot.repository.IChatSessionRepository;
import com.react.chatBot.entity.ChatMessages;
import com.react.websocket.dto.ChatWsResponse;
import org.modelmapper.ModelMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final AIService aiService;
    private final RetrieverService retrieverService;
    private final IChatSessionRepository chatSessionRepo;
    private final IChatMessageRepository chatMessageRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final ModelMapper modelMapper;

    public ChatService(
            AIService aiService,
            RetrieverService retrieverService,
            IChatSessionRepository chatSessionRepo,
            IChatMessageRepository chatMessageRepo, SimpMessagingTemplate messagingTemplate, ModelMapper modelMapper
    ) {
        this.aiService = aiService;
        this.retrieverService = retrieverService;
        this.chatSessionRepo = chatSessionRepo;
        this.chatMessageRepo = chatMessageRepo;
        this.messagingTemplate = messagingTemplate;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ChatResponseDTO chat(Long userId, String sessionId, String message) {

        String sid = (sessionId == null || sessionId.isBlank())
                ? UUID.randomUUID().toString()
                : sessionId;

        // 1) load/create session
        ChatSession session = chatSessionRepo.findBySessionKey(sid)
                .orElseGet(() -> {
                    ChatSession s = new ChatSession();
                    s.setSessionKey(sid);
                    s.setUserId(userId);
                    return chatSessionRepo.save(s);
                });

        // 2) load 20 message gần nhất của session (trước khi lưu message mới)
        List<ChatMessages> historyDesc =
                chatMessageRepo.findTop20BySession_IdOrderByCreatedAtDesc(session.getId());

        // đảo lại thành cũ -> mới
        Collections.reverse(historyDesc);

        String conversationContext = historyDesc.stream()
                .map(m -> (m.getRole() == MessageRole.USER ? "User: " : "Assistant: ") + m.getContent())
                .collect(Collectors.joining("\n"));

        // 3) lưu message user
        chatMessageRepo.save(ChatMessages.user(session, message));

        // 4) RAG context
        String ragContext = retrieverService.retrieveAsContext(message, 6);

        // 5) build prompt có history + rag
        String combinedContext = """
                Conversation so far:
                %s
                
                Relevant knowledge:
                %s
                """.formatted(
                conversationContext,
                (ragContext == null ? "" : ragContext)
        );

// 6) GỌI AI ĐÚNG SIGNATURE
        String answer;
        if (combinedContext.isBlank()) {
            answer = "Tôi không chắc";
        } else {
            answer = aiService.chat(message, combinedContext);
        }

        // 7) lưu assistant
        ChatMessages botMsg = chatMessageRepo.save(ChatMessages.assistant(session, answer));

        return new ChatResponseDTO(sid, botMsg.getId(), answer);
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

    @Transactional(readOnly = true)
    public List<ChatSession> findByUserIdOrderByUpdatedAtDesc(Long userId) {
        return chatSessionRepo.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    /**
     * WebSocket chat: push STATUS + SESSION (nếu tạo mới) + ASSISTANT_MESSAGE
     * Controller WS gọi hàm này thay vì chat() trực tiếp.
     */
    public void chatWs(Long userId, String sessionId, String message) {

        if (sessionId == null || sessionId.isBlank()) {
            // lần đầu làm WS: đơn giản nhất là bắt client tạo session trước
            ChatWsResponse err = ChatWsResponse.builder()
                    .type("ERROR")
                    .sessionId(null)
                    .content("Missing sessionId. Please create session first via HTTP.")
                    .messageId(null)
                    .build();
            messagingTemplate.convertAndSend("/topic/chat/errors", err);
            return;
        }

        String topic = "/topic/chat/" + sessionId;

        // STATUS: typing
        messagingTemplate.convertAndSend(topic,
                ChatWsResponse.builder()
                        .type("STATUS")
                        .sessionId(sessionId)
                        .content("typing")
                        .messageId(null)
                        .build()
        );

        try {
            ChatResponseDTO res = this.chat(userId, sessionId, message);

            // ASSISTANT_MESSAGE
            messagingTemplate.convertAndSend(topic,
                    ChatWsResponse.builder()
                            .type("ASSISTANT_MESSAGE")
                            .sessionId(res.getSessionId())
                            .content(res.getAnswer())
                            .messageId(res.getMessageId())
                            .build()
            );

            // STATUS: done
            messagingTemplate.convertAndSend(topic,
                    ChatWsResponse.builder()
                            .type("STATUS")
                            .sessionId(res.getSessionId())
                            .content("done")
                            .messageId(null)
                            .build()
            );

        } catch (Exception e) {
            messagingTemplate.convertAndSend(topic,
                    ChatWsResponse.builder()
                            .type("ERROR")
                            .sessionId(sessionId)
                            .content(e.getMessage())
                            .messageId(null)
                            .build()
            );
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDTO> getMessagesBySessionKey(Long userId, String sessionKey) {

        ChatSession session = chatSessionRepo.findBySessionKey(sessionKey)
                .orElseThrow(() -> new ConversationNotFound("ConversationNotFound", "Conversation not found"));

        // optional: check ownership
        if (userId != null && session.getUserId() != null &&
                !session.getUserId().equals(userId)) {
            throw new ConversationNotFound("ConversationNotFound", "Conversation not found");
        }

        return chatMessageRepo.findBySessionKeyOrderByCreatedAtAsc(sessionKey)
                .stream()
                .map(m -> modelMapper.map(m, ChatMessageDTO.class))
                .toList();
    }

}
