package com.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CourseCreateDTO(
    val id: Int? = null,
    @field:NotBlank(message = "Name is required")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    var name: String? = null,
    @field:NotBlank(message = "Description is required")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 100 characters")
    var description: String? = null,
    var active: Boolean? = null,
    @field:NotBlank(message = "Img is required")
    var urlImg: String? = null
)
