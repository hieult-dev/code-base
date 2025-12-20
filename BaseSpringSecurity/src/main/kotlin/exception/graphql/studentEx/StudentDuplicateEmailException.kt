package com.exception.graphql.studentEx

class StudentDuplicateEmailException : RuntimeException {
    constructor(message: String) : super("Email is duplicate: $message")
}