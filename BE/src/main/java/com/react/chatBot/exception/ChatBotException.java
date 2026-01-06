package com.react.chatBot.exception;

import com.react.exception.AppException;

public class ChatBotException extends AppException {
    protected ChatBotException(String code, String message) {
        super(code, message);
    }
}
