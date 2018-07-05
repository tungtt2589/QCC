package com.storm.quora.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.storm.quora.dto.QuestionDTO;
import com.storm.quora.util.RedisUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Override
    public List<QuestionDTO> getAllQuestion() throws Exception {
        List<QuestionDTO> questions = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getAllQuestion")
                .header("content-type", "application/json")
                .body("{}").asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject = results.getJSONObject(i);
            QuestionDTO question = new QuestionDTO();
            if (!jsonObject.isNull("user_id")) {
                question.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("topic_id")) {
                question.setTopicId(jsonObject.getLong("topic_id"));
            }
            if (!jsonObject.isNull("status_id")) {
                question.setStatusId(jsonObject.getLong("status_id"));
            }
            if (!jsonObject.isNull("question_id")) {
                question.setQuestionId(jsonObject.getLong("question_id"));
//                question.setCountUpVote(RedisUtil.getCountUpVote(String.valueOf(question.getQuestionId())));
//                question.setCountDownVote(RedisUtil.getCountDownVote(String.valueOf(question.getQuestionId())));
            }
            if (!jsonObject.isNull("content")) {
                question.setContent(jsonObject.getString("content"));
            }
            if (!jsonObject.isNull("created_time")) {
                question.setCreatedTime(jsonObject.getString("created_time"));
                question.setDiffTime(calculateDiffTime(question.getCreatedTime()));
            }
            if (!jsonObject.isNull("edited_time")) {
                question.setEditedTime(jsonObject.getString("edited_time"));
            }
            if (!jsonObject.isNull("description")) {
                question.setDescription(jsonObject.getString("description"));
            }
            if (!jsonObject.isNull("counted")) {
                question.setAnswerCount(jsonObject.getLong("counted"));
            }
            questions.add(question);
        }
        return questions;

    }

    @Override
    public List<QuestionDTO> getAllQuestionByTopic(Long topicId) throws Exception {
        List<QuestionDTO> questions = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getAllQuestionByTopic")
                .header("content-type", "application/json")
                .body("{\n \"topic_id\":\"" + topicId + "\"\n}\n").asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        for (int i = 0; i < results.length(); i++) {
            JSONObject jsonObject = results.getJSONObject(i);
            QuestionDTO question = new QuestionDTO();
            if (!jsonObject.isNull("user_id")) {
                question.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("topic_id")) {
                question.setTopicId(jsonObject.getLong("topic_id"));
            }
            if (!jsonObject.isNull("status_id")) {
                question.setStatusId(jsonObject.getLong("status_id"));
            }
            if (!jsonObject.isNull("question_id")) {
                question.setQuestionId(jsonObject.getLong("question_id"));
            }
            if (!jsonObject.isNull("content")) {
                question.setContent(jsonObject.getString("content"));
            }
            if (!jsonObject.isNull("created_time")) {
                question.setCreatedTime(jsonObject.getString("created_time"));
                question.setDiffTime(calculateDiffTime(question.getCreatedTime()));
            }
            if (!jsonObject.isNull("edited_time")) {
                question.setEditedTime(jsonObject.getString("edited_time"));
            }
            if (!jsonObject.isNull("description")) {
                question.setDescription(jsonObject.getString("description"));
            }
            if (!jsonObject.isNull("counted")) {
                question.setAnswerCount(jsonObject.getLong("counted"));
            }
            questions.add(question);
        }
        return questions;
    }

    @Override
    public QuestionDTO getQuestionById(Long questionId) throws Exception {
        QuestionDTO question = new QuestionDTO();
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getQuestionByParams")
                .header("content-type", "application/json")
                .body("{\n \"question_id\":\"" + questionId + "\"\n}\n").asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        JSONObject jsonObject = results.getJSONObject(0);
        if (!jsonObject.isNull("user_id")) {
            question.setUserId(jsonObject.getLong("user_id"));
        }
        if (!jsonObject.isNull("topic_id")) {
            question.setTopicId(jsonObject.getLong("topic_id"));
        }
        if (!jsonObject.isNull("status_id")) {
            question.setStatusId(jsonObject.getLong("status_id"));
        }
        if (!jsonObject.isNull("question_id")) {
            question.setQuestionId(jsonObject.getLong("question_id"));
        }
        if (!jsonObject.isNull("content")) {
            question.setContent(jsonObject.getString("content"));
        }
        if (!jsonObject.isNull("created_time")) {
            question.setCreatedTime(jsonObject.getString("created_time"));
            question.setDiffTime(calculateDiffTime(question.getCreatedTime()));
        }
        if (!jsonObject.isNull("edited_time")) {
            question.setEditedTime(jsonObject.getString("edited_time"));
        }
        if (!jsonObject.isNull("description")) {
            question.setDescription(jsonObject.getString("description"));
        }
        if (!jsonObject.isNull("counted")) {
            question.setAnswerCount(jsonObject.getLong("counted"));
        }
        return question;
    }

    @Override
    public int createQuestion(String content, String topicId, String description) throws Exception {
        int i = 0;
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/createQuestion")
                .header("content-type", "application/json")
                .body("{\n\"content\":\"" + content + "\"," +
                        "\n\"status_id\": \"1\"," +
                        "\n\"topic_id\": \"" + topicId + "\"," +
                        "\n\"description\": \"" + description + "\"," +
                        "\n\"user_id\": \"1\"\n}")
                .asJson();
        JSONObject jsonObject = response.getBody().getObject();
        i = jsonObject.getInt("status");
        return i;
    }

    private String calculateDiffTime(String postedTime) throws ParseException {
        String diffTime = "";
        if (!postedTime.equals("")) {

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date) format.parse(postedTime);
            long diff = new Date().getTime() - date.getTime();//as given

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hour = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            if (seconds < 60) {
                diffTime = seconds + "s";
            } else if (seconds > 60 && minutes < 60) {
                diffTime = minutes + " phút";
            } else if (minutes > 60 && hour < 24) {
                diffTime = hour + " giờ";
            } else {
                diffTime = days + " ngày";
            }
        }
        return diffTime;
    }
}
