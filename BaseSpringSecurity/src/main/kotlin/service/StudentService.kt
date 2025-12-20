package com.service

import com.entity.Course
import com.entity.Student
import com.exception.graphql.studentEx.StudentNotFoundException
import com.repository.CourseRepository
import com.repository.StudentCourseRepository
import com.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
open class StudentService(
    private val repo: StudentRepository,
    private val courseRepo: CourseRepository,
    private val studentCourseRepo: StudentCourseRepository
) {
    fun getAll(): List<Student> {
        return repo.findAll()
    }

    open fun create(student: Student): Student {
        return repo.save(student)
    }

    open fun delete(id: Int?): Boolean {
        return if (repo.existsById(id)) {
            repo.deleteById(id)
            true
        } else {
            false
        }
    }

    open fun update(id: Int?, newStudent: Student): Student {
        println("check in service")
        val existingStudent = repo.findById(id).orElseThrow { StudentNotFoundException("Student not found!") }
        existingStudent.name = newStudent.name
        existingStudent.email = newStudent.email
        existingStudent.age = newStudent.age
        existingStudent.major = newStudent.major
        return repo.save(existingStudent)
    }

    fun checkDuplicateEmail(email: String?): Boolean {
        return repo.existsByEmail(email)
    }

    fun existsByEmailAndIdNot(email: String?, id: Int?): Boolean {
        println("check dup in service")
        return repo.isDuplicateEmailOtherStudent(email, id)
    }

    fun findByName(name: String?): List<Student> {
        return repo.findByNameContainsIgnoreCase(name)
    }

    fun findById(id: Int?): Student {
        return repo.findById(id).orElseThrow { StudentNotFoundException("Student not found!") }
    }

fun findCoursesByStudentIds(studentIds: List<Int>): Map<Int, List<Course>> {
    // lấy về studentCourse chứa những student id cần truy vấn
    //List<StudentCourse>
    // ex: (1,101), (1,102), (2,101)
    val studentCourse = studentCourseRepo.findAllByStudentIdIn(studentIds)

    // lấy ra 1 list<Int> các courseId của list student cần tìm
    val courseIds = studentCourse.map { it.courseId }

    // tìm ra 1 Map<Int(id của course), Course> dựa trên list id course ở trên
    // ex: (101 - course), (102 - course)
    val coursesById = courseRepo.findAllById(courseIds).associateBy { it.id!! }

    // [ key(studentId) - List<Course> ]
    return studentCourse.groupBy({ it.studentId }, { coursesById[it.courseId]!! })
}

    fun findByUserName(name: String?): Student {
        return repo.findByUsername(name)
    }

    fun findByEmail(email: String?): Student {
        return repo.findByEmail(email).get()
    }

    fun getAllStudent(): List<Student> {
        return repo.findAllStudent()
    }
}