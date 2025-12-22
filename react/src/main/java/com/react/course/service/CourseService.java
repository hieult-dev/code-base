package com.react.course.service;

import com.react.course.dto.CourseDTO;
import com.react.course.entity.Course;
import com.react.course.repository.ICourseRepository;
import com.react.common.IService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements IService<Course, CourseDTO, Long> {

    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CourseDTO> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(c -> modelMapper.map(c, CourseDTO.class))
                .toList();
    }

    @Override
    public Optional<CourseDTO> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public CourseDTO create(CourseDTO dto) {
        return null;
    }

    @Override
    public CourseDTO update(Long aLong, CourseDTO dto) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
