package com.react.user.exception;

import com.react.exception.NotFoundException;

public class UserNotFound extends UserException implements NotFoundException {
    public UserNotFound(String code, String message) {
        super(code, message);
    }
}
