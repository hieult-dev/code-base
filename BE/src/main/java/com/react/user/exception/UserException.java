package com.react.user.exception;

import com.react.exception.AppException;

public class UserException extends AppException {
    protected UserException(String code, String message) {
        super(code, message);
    }
}
