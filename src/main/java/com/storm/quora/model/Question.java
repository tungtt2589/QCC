package com.storm.quora.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "question")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "topic_id", nullable = false)
    private long topicId;

    @Column(name = "status_id", nullable = false)
    private long statusId;

    @Column(name = "content", length = 155)
    private String content;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "edited_time")
    private String editedTime;

    public Question() {
    }

    public Question(long userId, long topicId, long statusId, String content, String createdTime, String editedTime) {
        this.userId = userId;
        this.topicId = topicId;
        this.statusId = statusId;
        this.content = content;
        this.createdTime = createdTime;
        this.editedTime = editedTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTopicId() {
        return topicId;
    }

    public void setTopicId(long topicId) {
        this.topicId = topicId;
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
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
}
