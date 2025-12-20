package com.exception.graphql.courseEx

class CourseNameDuplicateException: RuntimeException {
    constructor(message: String) : super("Name course is duplicate: $message")
}