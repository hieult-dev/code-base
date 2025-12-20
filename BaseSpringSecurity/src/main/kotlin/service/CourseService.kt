package com.service


import com.entity.Course
import com.exception.graphql.courseEx.CourseNotFoundException
import com.repository.CourseRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.*

@Service
open class CourseService(
    private val courseRepo: CourseRepository
) {
    fun getAllCourse(): List<Course> {
        return courseRepo.findCourseByActive()
    }

    fun getAllCourseActivePage(pageable: Pageable): Page<Course> {
        return courseRepo.findAll(pageable)
    }

    fun checkDuplicateName(name: String?): Boolean {
        return courseRepo.existsCoursesByName(name)
    }

    fun createCourse(course: Course): Course {
        return courseRepo.save(course)
    }

    fun updateCourse(updateCourse: Course): Course {
        val existingCourse = courseRepo.findById(updateCourse.id)
        if (existingCourse.isPresent) {
            existingCourse.get().name = updateCourse.name
            existingCourse.get().description = updateCourse.description
            existingCourse.get().active = updateCourse.active
            existingCourse.get().urlImg = updateCourse.urlImg
            return courseRepo.save(updateCourse)
        } else throw CourseNotFoundException("Course not found!")
    }

    fun findById(id: Int): Course {
        return courseRepo.findById(id).orElseThrow { CourseNotFoundException("Course not found!") }
    }

    fun deleteCourse(id: Int): Boolean {
        return if (courseRepo.existsById(id)) {
            courseRepo.deleteById(id)
            true
        } else {
            false
        }
    }

}