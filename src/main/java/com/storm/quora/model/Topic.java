package com.storm.quora.model;

import com.storm.quora.QuoraApplication;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "topic")
@XmlRootElement
@NamedQueries({@NamedQuery(name = "Topic.findAll", query = "SELECT p FROM Topic p")})
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "topic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long topicId;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "name", length = 155)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_time")
    private String createdTime;

    @Column(name = "edited_time")
    private String editedTime;

    @Column(name = "status_id")
    private long statusId;

    public Topic() {
    }

    public Topic(long userId, String name, String description, String createdTime, String editedTime, long statusId) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.createdTime = createdTime;
        this.editedTime = editedTime;
        this.statusId = statusId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
}
