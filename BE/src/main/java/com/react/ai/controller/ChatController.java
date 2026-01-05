package com.react.ai.controller;

import com.react.ai.rag.service.RetrieverService;
import com.react.ai.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final AIService aiService;
    private final RetrieverService retrieverService;

    public ChatController(AIService aiService, RetrieverService retrieverService) {
        this.aiService = aiService;
        this.retrieverService = retrieverService;
    }

    @PostMapping
    public String chat(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody String question
    ) {
        String ragContext =
                retrieverService.retrieveAsContext(question, 6);

        if (ragContext.isBlank()) {
            return "Tôi không chắc";
        }

        return aiService.chat(question, ragContext);
    }

}
