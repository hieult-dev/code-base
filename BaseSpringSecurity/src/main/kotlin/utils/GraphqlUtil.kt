package com.utils

import com.dto.CustomPageable
import org.springframework.data.domain.*

object GraphqlUtil {
    fun toPageable(p: CustomPageable?): Pageable {
        val page = (p?.page ?: 0).coerceAtLeast(0)
        val size = (p?.size ?: 6).coerceIn(1, 200) // Giới hạn size để tránh query quá nặng
        return PageRequest.of(page, size)
    }
}