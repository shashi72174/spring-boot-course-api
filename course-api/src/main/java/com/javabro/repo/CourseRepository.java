package com.javabro.repo;

import com.javabro.entity.Course;
import com.javabro.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTopic_Id(Long id);
}
