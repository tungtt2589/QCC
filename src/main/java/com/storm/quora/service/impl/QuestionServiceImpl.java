package com.storm.quora.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.model.Question;
import com.storm.quora.repository.QuestionRepository;
import com.storm.quora.service.QuestionService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
//    @Autowired
//    QuestionRepository questionRepository;

    @Override
    public List<QuestionDTO> getAllQuestion() throws Exception {
        List<QuestionDTO> questions = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getAllQuestion")
                .header("content-type", "application/json")
                .body("{}").asJson();
//        .body("{\n\"content\":\"Đê ma ma\",\n\"status_id\": \"1\",\n\"question_id\": \"1\",\n\"user_id\": \"1\"\n}").asString();
//        Gson gson = new Gson();
//        Question myObject = gson.fromJson(responseJSONString, Question.class);
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        for (int i = 0; i < results.length(); i ++){
            JSONObject jsonObject = results.getJSONObject(i);
            QuestionDTO question = new QuestionDTO();
            if (!jsonObject.isNull("user_id")){
                question.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("topic_id")){
                question.setTopicId(jsonObject.getLong("topic_id"));
            }
            if (!jsonObject.isNull("status_id")){
                question.setStatucId(jsonObject.getLong("status_id"));
            }
            if (!jsonObject.isNull("question_id")){
                question.setQuestionId(jsonObject.getLong("question_id"));
            }
            if (!jsonObject.isNull("content")){
                question.setContent(jsonObject.getString("content"));
            }
            if (!jsonObject.isNull("created_time")){
                question.setCreatedTime(jsonObject.getString("created_time"));
            }
            if (!jsonObject.isNull("edited_time")){
                question.setEditedTime(jsonObject.getString("edited_time"));
            }
            questions.add(question);
        }
        return questions;

    }

    @Override
    public QuestionDTO getQuestionById(Long questionId) throws Exception {
        QuestionDTO question = new QuestionDTO();
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getAllQuestion")
                .header("content-type", "application/json")
                .body("{\n \"question_id\":\""+questionId+"\"\n}\n").asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
            JSONObject jsonObject = results.getJSONObject(0);
            if (!jsonObject.isNull("user_id")){
                question.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("topic_id")){
                question.setTopicId(jsonObject.getLong("topic_id"));
            }
            if (!jsonObject.isNull("status_id")){
                question.setStatucId(jsonObject.getLong("status_id"));
            }
            if (!jsonObject.isNull("question_id")){
                question.setQuestionId(jsonObject.getLong("question_id"));
            }
            if (!jsonObject.isNull("content")){
                question.setContent(jsonObject.getString("content"));
            }
            if (!jsonObject.isNull("created_time")){
                question.setCreatedTime(jsonObject.getString("created_time"));
            }
            if (!jsonObject.isNull("edited_time")){
                question.setEditedTime(jsonObject.getString("edited_time"));
            }
        return question;
    }

    @Override
    public int createQuestion(String content) throws Exception {
        int i = 0;
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/createQuestion")
                .header("content-type", "application/json")
                .body("{\n\"content\":\""+content+"\"," +
                        "\n\"status_id\": \"1\"," +
                        "\n\"topic_id\": \"1\"," +
                        "\n\"user_id\": \"1\"\n}")
                .asJson();
        JSONObject jsonObject = response.getBody().getObject();
        i = jsonObject.getInt("status");
        return i;
    }
}
