package com.react.course.exception;

import com.react.exception.NotFoundException;

public class CourseNotFound extends CourseException implements NotFoundException {
    public CourseNotFound(String code, String message) {
        super(code, message);
    }
}
