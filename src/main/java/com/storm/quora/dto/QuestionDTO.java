package com.storm.quora.dto;

import java.io.Serializable;
import java.util.Date;

public class QuestionDTO extends BaseDTO implements Serializable {
    public String getKeySet() {
        return keySet;
    }
    public static enum COLUMNS{QUESTION_ID,CONTENT,TOPIC_ID,USER_ID,CREATED_TIME,EDITED_TIME, STATUS_ID}
    private long questionId;
    private long topicId;
    private long userId;
    private String content;
    private String createdTime;
    private String editedTime;
    private long statucId;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
