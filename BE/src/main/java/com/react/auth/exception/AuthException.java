package com.react.auth.exception;

import com.react.exception.AppException;

public class AuthException extends AppException {
    protected AuthException(String code, String message) {
        super(code, message);
    }
}
