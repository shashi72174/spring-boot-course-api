package com.javabro.controller;

import com.javabro.dto.TopicDTO;
import com.javabro.entity.Topic;
import com.javabro.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public ResponseEntity<List<TopicDTO>> getAllTopics() {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getAllTopics());
    }

    @GetMapping("/topics/{id}")
    public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopicById(id));
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicDTO> getAllTopics(@RequestBody TopicDTO topicDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.saveTopic(topicDTO));
    }

    @PutMapping("/topics/{id}")
    public ResponseEntity<TopicDTO> getAllTopics(@RequestBody TopicDTO topicDTO, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.updateTopic(topicDTO, id));
    }

    @DeleteMapping("/topics/{id}")
    public ResponseEntity<TopicDTO> deleteTopic(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.deleteTopicById(id));
    }
}
