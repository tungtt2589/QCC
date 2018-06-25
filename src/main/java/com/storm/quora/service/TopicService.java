package com.storm.quora.service;

import com.storm.quora.dto.TopicDTO;
import com.storm.quora.model.Topic;

import java.util.List;

public interface TopicService {
    List<TopicDTO> getAllTopic() throws Exception;
}
