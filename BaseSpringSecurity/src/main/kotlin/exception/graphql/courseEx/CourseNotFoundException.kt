package com.exception.graphql.courseEx

class CourseNotFoundException: RuntimeException {
    constructor(message: String): super("Course not found: $message")
}