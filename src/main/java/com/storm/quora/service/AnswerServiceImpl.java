package com.storm.quora.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.storm.quora.dto.AnswerDTO;
import com.storm.quora.service.AnswerService;
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
public class AnswerServiceImpl implements AnswerService {

    @Override
    public List<AnswerDTO> getAllAnswer(String questionId) throws Exception {
        List<AnswerDTO> answerDTOS = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getAllAnswer")
                .header("content-type", "application/json")
                .body("{\n \"question_id\":\""+questionId+"\"\n}\n").asJson();

        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        for (int i = 0; i < results.length(); i ++){
            JSONObject jsonObject = results.getJSONObject(i);
            AnswerDTO answerDTO = new AnswerDTO();
            if (!jsonObject.isNull("user_id")){
                answerDTO.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("answer_id")){
                answerDTO.setAnswerId(jsonObject.getLong("answer_id"));
            }
            if (!jsonObject.isNull("status_id")){
                answerDTO.setStatusId(jsonObject.getLong("status_id"));
            }
            if (!jsonObject.isNull("question_id")){
                answerDTO.setQuestionId(jsonObject.getLong("question_id"));
            }
            if (!jsonObject.isNull("content")){
                answerDTO.setContent(jsonObject.getString("content"));
            }
            if (!jsonObject.isNull("created_time")){
                answerDTO.setCreatedTime(jsonObject.getString("created_time"));
                answerDTO.setDiffTime(calculateDiffTime(answerDTO.getCreatedTime()));
            }
            if (!jsonObject.isNull("edited_time")){
                answerDTO.setEditedTime(jsonObject.getString("edited_time"));
            }
            answerDTOS.add(answerDTO);
        }
        return answerDTOS;
    }

    @Override
    public int createAnswer(String questionId, String content) throws Exception {
        int i = 0;
        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/createAnswer")
                .header("content-type", "application/json")
                .body("{\n\"content\":\""+content+"\"," +
                        "\n\"status_id\": \"1\"," +
                        "\n\"question_id\": \""+questionId+"\"," +
                        "\n\"user_id\": \"1\"\n}")
                .asJson();
        JSONObject jsonObject = response.getBody().getObject();
        i = jsonObject.getInt("status");
        return i;
    }

    private String calculateDiffTime(String postedTime) throws ParseException {
        String diffTime = "";
        if (!postedTime.equals("")){

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date)format.parse(postedTime);
            long diff = new Date().getTime() - date.getTime();//as given

            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hour = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            if (seconds < 60){
                diffTime = seconds+"s";
            }else if (seconds > 60 && minutes < 60){
                diffTime = minutes+" phút";
            }else if (minutes > 60 && hour < 24){
                diffTime = hour+" giờ";
            }else {
                diffTime = days+" ngày";
            }
        }
        return diffTime;
    }
}
