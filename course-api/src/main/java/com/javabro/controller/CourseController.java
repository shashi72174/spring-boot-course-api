package com.javabro.controller;

import com.javabro.dto.CourseDTO;
import com.javabro.dto.TopicDTO;
import com.javabro.service.CourseService;
import com.javabro.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController
{

    @Autowired
    private CourseService courseService;

    @GetMapping("/topics/{topicId}/courses")
    public ResponseEntity<List<CourseDTO>> getAllCourses(@PathVariable Long topicId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCoursesByTopicId(topicId));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> getTopicById(@PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(courseId));
    }

    @PostMapping("/topics/{topicId}/courses")
    public ResponseEntity<CourseDTO> saveCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long topicId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(courseDTO, topicId));
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.updateCourse(courseDTO, courseId));
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<CourseDTO> deleteCourse(@PathVariable Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.deleteCourseById(courseId));
    }
}
