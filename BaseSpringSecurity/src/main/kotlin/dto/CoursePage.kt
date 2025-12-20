package com.dto

import com.entity.Course

data class CoursePage (
    val items: List<Course>,
    val totalElements: Long,
    val totalPages: Int,
    val page: Int,
    val size: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
)
