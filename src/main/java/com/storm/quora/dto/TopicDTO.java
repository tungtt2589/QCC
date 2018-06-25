package com.storm.quora.dto;

import java.io.Serializable;
import java.util.Date;

public class TopicDTO extends BaseDTO implements Serializable {
    public String getKeySet() {
        return keySet;
    }
    public static enum COLUMNS{TOPIC_ID,USER_ID,NAME,DESCRIPTION,CREATED_TIME,EDITED_TIME, STATUS_ID}
    private long topicId;
    private long userId;
    private String name;
    private String description;
    private String createdTime;
    private String editedTime;
    private long statucId;

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEditedTime() {
        return editedTime;
    }

    public void setEditedTime(String editedTime) {
        this.editedTime = editedTime;
    }

    public long getStatucId() {
        return statucId;
    }

    public void setStatucId(long statucId) {
        this.statucId = statucId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
