package com.react.api.rest.course;

import com.react.dto.course.CourseDTO;
import com.react.model.course.Course;
import com.react.service.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/getAll")
    public List<CourseDTO> getAll() {
        return courseService.getAll();
    }

}
