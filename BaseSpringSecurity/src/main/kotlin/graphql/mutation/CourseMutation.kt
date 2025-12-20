package com.graphql.mutation

import com.dto.CourseCreateDTO
import com.entity.Course
import com.exception.graphql.courseEx.CourseNameDuplicateException
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import com.service.CourseService
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@DgsComponent
@Validated
open class CourseMutation(
    private val courseService: CourseService,
) {
    @DgsMutation
    open fun createCourse(@Valid @InputArgument course: CourseCreateDTO): Course {
        val newCourse = Course(
            name = course.name,
            description = course.description,
            active = course.active,
            urlImg = course.urlImg
        )
        if (!courseService.checkDuplicateName(course.name)) {
            return courseService.createCourse(newCourse)
        } else {
            throw CourseNameDuplicateException("${course.name} is duplicate!")
        }
    }

    @DgsMutation
    open fun updateCourse(@Valid @InputArgument course: CourseCreateDTO): Course {
        val updateCourse = Course(
            id = course.id,
            name = course.name,
            description = course.description,
            active = course.active,
            urlImg = course.urlImg
        )
        if (!courseService.checkDuplicateName(course.name)) {
            return courseService.updateCourse(updateCourse)
        } else {
            throw CourseNameDuplicateException("${course.name} is duplicate!")
        }
    }

    @DgsMutation
    open fun deleteCourse(@InputArgument id: Int): Boolean {
        return courseService.deleteCourse(id)
    }
}