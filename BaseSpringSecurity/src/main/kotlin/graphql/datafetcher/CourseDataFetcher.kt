package com.graphql.datafetcher

import com.dto.CustomPageable
import com.entity.Course
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.service.CourseService
import com.utils.GraphqlUtil
import org.springframework.data.domain.Page
import com.dto.CoursePage

@DgsComponent
class CourseDataFetcher(
    private val courseService:  CourseService
) {
    @DgsQuery
    fun getAllCourseActive(): List<Course> {
        return courseService.getAllCourse()
    }

    @DgsQuery
    fun getAllCourseActivePage(pageable: CustomPageable): CoursePage  {
        val pageReq = GraphqlUtil.toPageable(pageable)
        val page: Page<Course> = courseService.getAllCourseActivePage(pageReq)
        return CoursePage(
            items = page.content,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            page = page.number,
            size = page.size,
            hasNext = page.hasNext(),
            hasPrevious = page.hasPrevious(),
        )
    }
}