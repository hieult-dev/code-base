package com.react.chatBot.exception;

import com.react.exception.NotFoundException;

public class ConversationNotFound extends ChatBotException implements NotFoundException {
    public ConversationNotFound(String code, String message) {
        super(code, message);
    }
}
