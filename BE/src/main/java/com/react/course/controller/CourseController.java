package com.react.course.controller;

import com.react.course.dto.CourseDTO;
import com.react.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/getAllCoursePublic")
    public List<CourseDTO> getAll() {
        return courseService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllCourseForAdmin")
    public List<CourseDTO> getAllForAdmin() {
        return courseService.getAllForAdmin();
    }

    @GetMapping("/getCourseById/{id}")
    public CourseDTO getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

}
