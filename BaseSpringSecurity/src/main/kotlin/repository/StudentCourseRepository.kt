package com.repository

import com.entity.StudentCourse
import com.entity.StudentCourseId
import org.springframework.data.jpa.repository.JpaRepository

interface StudentCourseRepository: JpaRepository<StudentCourse, StudentCourseId> {
    fun findAllByStudentIdIn(studentIds: List<Int>): List<StudentCourse>
}