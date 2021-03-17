package com.podlobby.podlobby.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
//Does this need the s?
@Table (name = "responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) unsigned")
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private Timestamp createdAt;

    @Column(columnDefinition = "TINYINT")
    private int acceptedStatus;

    @ManyToOne
    private User user;

    @ManyToOne
    private Request request;

    public Response(){}

    public Response(long id, String content, Timestamp createdAt, User user, Request request) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.request = request;
    }

    public Response(String content, Timestamp createdAt, User user, Request request) {
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public int getAcceptedStatus() {
        return acceptedStatus;
    }

    public void setAcceptedStatus(int acceptedStatus) {
        this.acceptedStatus = acceptedStatus;
    }
}
