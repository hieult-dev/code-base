package com.react.enrollment.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EnrollmentDTO {
    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDateTime enrolledAt = LocalDateTime.now();
}
