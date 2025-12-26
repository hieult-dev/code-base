package com.react.auth.exception;

public class LoginException extends AuthException{
    public LoginException(String code, String message) {
        super(code, message);
    }
}
