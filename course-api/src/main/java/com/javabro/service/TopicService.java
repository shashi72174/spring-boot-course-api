package com.javabro.service;

import com.javabro.dto.TopicDTO;
import com.javabro.entity.Topic;
import com.javabro.error.RecordNotFoundException;
import com.javabro.repo.TopicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    /*private List<TopicDTO> topics = new ArrayList<>();

    {
        topics.add(new TopicDTO(1l, "topic1", "topic-1"));
        topics.add(new TopicDTO(2l, "topic2", "topic-2"));
        topics.add(new TopicDTO(3l, "topic3", "topic-3"));
        topics.add(new TopicDTO(4l, "topic4", "topic-4"));

    }*/
    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll().stream().map(topic -> new TopicDTO(topic.getId(),topic.getName(), topic.getDescription())).collect(Collectors.toList());
        //return topics;
    }

    public TopicDTO getTopicById(Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if(optionalTopic.isPresent())
            return optionalTopic.map(topic -> new TopicDTO(topic.getId(),topic.getName(), topic.getDescription())).get();
        else
            throw new RecordNotFoundException("no topic present with id "+id);
        //return topics.stream().filter(topicDTO -> topicDTO.getId().longValue()==id.longValue()).findFirst().get();
    }

    public TopicDTO saveTopic(TopicDTO topicDTO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDTO, topic);
        topic = topicRepository.save(topic);
        BeanUtils.copyProperties(topic, topicDTO);
        //topics.add(topicDTO);
        return topicDTO;
    }

    public TopicDTO updateTopic(TopicDTO topicDTO, Long id) {
        /*deleteTopicById(id);
        topics.add(topicDTO);*/
        Topic topic = new Topic();
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if(optionalTopic.isPresent()) {
            BeanUtils.copyProperties(topicDTO, topic);
            topic.setId(id);
            topic = topicRepository.save(topic);
            BeanUtils.copyProperties(topic, topicDTO);
        }else
            throw new RecordNotFoundException("no topic present with id "+id);
        return topicDTO;
    }

    public TopicDTO deleteTopicById(Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if(optionalTopic.isPresent())
            topicRepository.deleteById(id);
        else
            throw new RecordNotFoundException("no topic present with id "+id);
        //TopicDTO topicDTO = topics.stream().filter(topicDTO1 -> topicDTO1.getId().longValue()==id.longValue()).findFirst().get();
        //topics.remove(topicDTO);
        TopicDTO topicDTO = new TopicDTO();
        BeanUtils.copyProperties(optionalTopic.get(), topicDTO);
        return topicDTO;
    }
}