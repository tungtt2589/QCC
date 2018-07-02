package com.storm.quora.service;

import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.model.Question;

import java.util.List;

public interface QuestionService {
    List<QuestionDTO> getAllQuestion() throws Exception;
    List<QuestionDTO> getAllQuestionByTopic(Long topicId) throws Exception;
    QuestionDTO getQuestionById(Long questionId) throws Exception;
    int createQuestion(String content, String topicId, String description) throws Exception;
}
