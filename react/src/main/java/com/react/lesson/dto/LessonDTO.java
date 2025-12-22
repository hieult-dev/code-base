package com.react.lesson.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LessonDTO {
    private Long id;
    private Long courseId;
    private String title;
    private String videoUrl;
    private Integer lessonOrder;
    private LocalDateTime createdAt = LocalDateTime.now();
}
