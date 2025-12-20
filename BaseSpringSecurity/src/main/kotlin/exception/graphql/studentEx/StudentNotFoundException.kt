package com.exception.graphql.studentEx

class StudentNotFoundException: RuntimeException {
    constructor(message: String) : super("Not found student : $message")
}