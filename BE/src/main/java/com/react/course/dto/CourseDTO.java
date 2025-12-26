package com.react.course.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private Double price = 0.0;
    private String instructorId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Boolean active;
}
