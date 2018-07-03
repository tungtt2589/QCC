package com.storm.quora.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.storm.quora.dto.TopicDTO;
import com.storm.quora.model.Topic;
import com.storm.quora.repository.TopicRepository;
import com.storm.quora.service.TopicService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    TopicRepository topicRepository;

    @Override
    public List<TopicDTO> getAllTopic() throws Exception {

        List<TopicDTO> topics = new ArrayList<>();

        HttpResponse<JsonNode> response = Unirest.post("http://171.244.3.242:7070/getAllTopic")
                .header("content-type", "application/json")
                .body("{}").asJson();
        JSONObject myObj = response.getBody().getObject();
        JSONArray results = myObj.getJSONArray("data");
        for (int i = 0; i < results.length(); i ++){
            JSONObject jsonObject = results.getJSONObject(i);
            TopicDTO topic = new TopicDTO();
            if (!jsonObject.isNull("user_id")){
                topic.setUserId(jsonObject.getLong("user_id"));
            }
            if (!jsonObject.isNull("topic_id")){
                topic.setTopicId(jsonObject.getLong("topic_id"));
            }
            if (!jsonObject.isNull("status_id")){
                topic.setStatucId(jsonObject.getLong("status_id"));
            }
            if (!jsonObject.isNull("name")){
                topic.setName(jsonObject.getString("name"));
            }
            if (!jsonObject.isNull("description")){
                topic.setDescription(jsonObject.getString("description"));
            }
            if (!jsonObject.isNull("created_time")){
                topic.setCreatedTime(jsonObject.getString("created_time"));
            }
            if (!jsonObject.isNull("edited_time")){
                topic.setEditedTime(jsonObject.getString("edited_time"));
            }
            topics.add(topic);
        }
        return topics;
    }
}
