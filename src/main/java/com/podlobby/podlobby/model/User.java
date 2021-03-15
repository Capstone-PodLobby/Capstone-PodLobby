package com.podlobby.podlobby.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) unsigned")
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Timestamp joinedAt;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    @Column(columnDefinition = "TEXT")
    private String backgroundImage;

    @Column(columnDefinition = "TINYINT")
    private int isAdmin;

    @Column(columnDefinition = "TEXT")
    private String aboutMe;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Podcast> podcasts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Response> responseList;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "followed_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "follow_id")})
    private List<User> users = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "followed_users",
            joinColumns = {@JoinColumn(name = "follow_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> followers = new ArrayList<>();

    public User(){ }

    public User(User copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.password = copy.password;
        this.email = copy.email;
        this.joinedAt = copy.joinedAt;
        this.profileImage = copy.profileImage;
        this.backgroundImage = copy.backgroundImage;
        this.isAdmin = copy.isAdmin;
        this.aboutMe = copy.aboutMe;
        this.podcasts = copy.podcasts;
        this.comments = copy.comments;
        this.requests = copy.requests;
        this.responseList = copy.responseList;
        this.users = copy.users;
        this.followers = copy.followers;
    }

    public User(long id, String backgroundImage, String username, String password, String email, Timestamp joinedAt, String profileImage, int isAdmin, String aboutMe, List<Podcast> podcasts, List<Comment> comments, List<Request> requests, List<Response> responseList, List<User> users, List<User> followers) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.joinedAt = joinedAt;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.isAdmin = isAdmin;
        this.aboutMe = aboutMe;
        this.podcasts = podcasts;
        this.comments = comments;
        this.requests = requests;
        this.responseList = responseList;
        this.users = users;
        this.followers = followers;
    }

    public User(String username, String backgroundImage, String password, String email, Timestamp joinedAt, String profileImage, int isAdmin, String aboutMe, List<Podcast> podcasts, List<Comment> comments, List<Request> requests, List<Response> responseList, List<User> users, List<User> followers) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.joinedAt = joinedAt;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.isAdmin = isAdmin;
        this.aboutMe = aboutMe;
        this.podcasts = podcasts;
        this.comments = comments;
        this.requests = requests;
        this.responseList = responseList;
        this.users = users;
        this.followers = followers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Timestamp joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
