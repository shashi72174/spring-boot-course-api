package com.javabro.service;

import com.javabro.dto.CourseDTO;
import com.javabro.dto.TopicDTO;
import com.javabro.entity.Course;
import com.javabro.entity.Topic;
import com.javabro.error.RecordNotFoundException;
import com.javabro.repo.CourseRepository;
import com.javabro.repo.TopicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    public List<CourseDTO> getAllCoursesByTopicId(Long topicId) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent())
            return courseRepository.findByTopic_Id(topicId).stream().map(course -> {
            Topic topic1 = course.getTopic();
            TopicDTO topicDTO = TopicDTO.builder().id(topic1.getId()).name(topic1.getName()).description(topic1.getDescription()).build();
            CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName(), course.getDescription(), topicDTO);
            return courseDTO;
        }).collect(Collectors.toList());
        else
            throw new RecordNotFoundException("no topic present with id " + topicId);
    }

    public CourseDTO getCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent())
            return optionalCourse.map(course -> {
            Topic topic1 = course.getTopic();
            TopicDTO topicDTO = TopicDTO.builder().id(topic1.getId()).name(topic1.getName()).description(topic1.getDescription()).build();
            CourseDTO courseDTO = new CourseDTO(course.getId(), course.getName(), course.getDescription(), topicDTO);
            return courseDTO;
        }).get();
        else
            throw new RecordNotFoundException("no course present with id " + courseId);
    }

    public CourseDTO saveCourse(CourseDTO courseDTO, Long topicId) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
        if (optionalTopic.isPresent())
            course.setTopic(optionalTopic.get());
        else
            throw new RecordNotFoundException("no topic present with id " + topicId);
        course = courseRepository.save(course);
        TopicDTO topicDTO = TopicDTO.builder().id(optionalTopic.get().getId()).name(optionalTopic.get().getName()).description(optionalTopic.get().getDescription()).build();
        courseDTO = new CourseDTO(course.getId(), course.getName(), course.getDescription(), topicDTO);
        return courseDTO;
    }

    public CourseDTO updateCourse(CourseDTO courseDTO, Long courseId) {
        Course course = null;
        if (courseRepository.findById(courseId).isPresent()) course = courseRepository.findById(courseId).get();
        else throw new RecordNotFoundException("no course present with id " + courseId);

        course.setName(courseDTO.getName());
        course.setDescription(courseDTO.getDescription());
        course.setId(courseId);
        Optional<Topic> optionalTopic = topicRepository.findById(courseDTO.getTopicDTO().getId());
        if (optionalTopic.isPresent()) course.setTopic(optionalTopic.get());
        else throw new RecordNotFoundException("no topic present with id " + courseDTO.getTopicDTO().getId());
        courseRepository.save(course);
        TopicDTO topicDTO = TopicDTO.builder().id(optionalTopic.get().getId()).name(optionalTopic.get().getName()).description(optionalTopic.get().getDescription()).build();
        courseDTO = new CourseDTO(course.getId(), course.getName(), course.getDescription(), topicDTO);
        return courseDTO;
    }

    public CourseDTO deleteCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) courseRepository.deleteById(courseId);
        else throw new RecordNotFoundException("no course present with id " + courseId);
        CourseDTO courseDTO = new CourseDTO();
        TopicDTO topicDTO = TopicDTO.builder().id(optionalCourse.get().getTopic().getId()).name(optionalCourse.get().getTopic().getName()).description(optionalCourse.get().getTopic().getDescription()).build();
        BeanUtils.copyProperties(optionalCourse.get(), courseDTO);
        courseDTO.setTopicDTO(topicDTO);
        return courseDTO;
    }
}
