package com.react.chatBot.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIServiceConfig {
    @Bean
    public AIService aiService(ChatLanguageModel model) {
        return AiServices.create(AIService.class, model);
    }
}
