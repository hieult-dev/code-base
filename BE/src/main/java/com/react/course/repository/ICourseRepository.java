package com.react.course.repository;

import com.react.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICourseRepository extends JpaRepository<Course,Long> {

    @Query("SELECT c FROM Course c WHERE c.active = true")
    List<Course> getAllForUser();

    @Query("SELECT c FROM Course c WHERE c.id =:id")
    Optional<Course> getCourseById(@Param("id") Long id);
}
