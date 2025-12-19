package com.react.service.course;

import com.react.dto.course.CourseDTO;
import com.react.model.course.Course;
import com.react.repository.course.ICourseRepository;
import com.react.service.IService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements IService<Course, CourseDTO, Long> {

    @Autowired
    private ICourseRepository courseRepository;
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
