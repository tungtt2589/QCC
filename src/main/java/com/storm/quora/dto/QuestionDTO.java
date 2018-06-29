package com.storm.quora.dto;

import java.io.Serializable;
import java.util.Date;

public class QuestionDTO extends BaseDTO implements Serializable {
    public String getKeySet() {
        return keySet;
    }
    public static enum COLUMNS{QUESTION_ID,CONTENT,TOPIC_ID,USER_ID,CREATED_TIME,EDITED_TIME, STATUS_ID, DESCRIPTION}
    private long questionId;
    private long topicId;
    private long userId;
    private String content;
    private String description;
    private String createdTime;
    private String diffTime;
    private String editedTime;
    private long statucId;
    private long answerCount;

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

    public String getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(String diffTime) {
        this.diffTime = diffTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(long answerCount) {
        this.answerCount = answerCount;
    }
}
