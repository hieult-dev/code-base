package com.graphql.dataloader

import com.entity.Course
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsDataLoader
import com.service.StudentService
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsComponent
class StudentDataLoader(
    private val studentService: StudentService
) {
    @DgsDataLoader(name = "courses")
    class CourseDataLoader(
        private val studentService: StudentService
    ) : MappedBatchLoader<Int, List<Course>> {
        override fun load(keys: Set<Int>): CompletionStage<Map<Int, List<Course>>> {
            return CompletableFuture.supplyAsync {
                // service trả Map<studentId, List<Course>>
                studentService.findCoursesByStudentIds(keys.toList())
            }
        }
    }
}