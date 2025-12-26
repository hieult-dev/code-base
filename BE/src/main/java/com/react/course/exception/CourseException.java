package com.react.course.exception;

import com.react.exception.AppException;

public class CourseException extends AppException {
    protected CourseException(String code, String message) {
        super(code, message);
    }
}
