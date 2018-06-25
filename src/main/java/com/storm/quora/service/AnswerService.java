package com.storm.quora.service;

import com.storm.quora.dto.AnswerDTO;

import java.util.List;

public interface AnswerService {
    List<AnswerDTO> getAllAnswer(String questionId) throws Exception;
    int createAnswer(String questionId, String content) throws Exception;
}
