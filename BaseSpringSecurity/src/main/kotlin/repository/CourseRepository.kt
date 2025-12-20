package com.repository

import com.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository : JpaRepository<Course, Int> {
    @Query("select c from Course c where c.active = true")
    fun findCourseByActive(): List<Course>

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Course c WHERE c.name = :name")
    fun existsCoursesByName(@Param("name") name: String?): Boolean

}