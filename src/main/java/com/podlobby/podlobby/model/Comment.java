package com.podlobby.podlobby.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table (name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) unsigned")
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @Column
    private Timestamp createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Podcast podcast;

    public Comment(){}

    public Comment(long id, String comment, Timestamp createdAt, User user, Podcast podcast) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
        this.user = user;
        this.podcast = podcast;
    }

    public Comment(String comment, Timestamp createdAt, User user, Podcast podcast) {
        this.comment = comment;
        this.createdAt = createdAt;
        this.user = user;
        this.podcast = podcast;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Podcast getPodcast() {
        return podcast;
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
    }
}
