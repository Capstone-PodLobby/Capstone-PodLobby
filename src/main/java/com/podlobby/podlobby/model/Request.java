package com.podlobby.podlobby.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table (name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) unsigned")
    private long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    private List<Response> responseList;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private int isActive;

    @Column(columnDefinition = "int(11) unsigned")
    private int guestCount;

    @Column
    private Timestamp createdAt;

    public Request() { }

    public Request(long id, User user, List<Response> responseList, String title, String description, int isActive, int guestCount, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.responseList = responseList;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
        this.guestCount = guestCount;
        this.createdAt = createdAt;
    }

    public Request(User user, List<Response> responseList, String title, String description, int isActive, int guestCount, Timestamp createdAt) {
        this.user = user;
        this.responseList = responseList;
        this.title = title;
        this.description = description;
        this.isActive = isActive;
        this.guestCount = guestCount;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
