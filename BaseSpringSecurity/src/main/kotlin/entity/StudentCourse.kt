package com.entity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Table
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable

@Entity
@Table(name = "student_course")
@IdClass(StudentCourseId::class)
data class StudentCourse(
    @Id
    @Column(name = "student_id")
    val studentId: Int = 0,

    @Id
    @Column(name = "course_id")
    val courseId: Int = 0
)

data class StudentCourseId(
    val studentId: Int = 0,
    val courseId: Int = 0
) : Serializable