package com.podlobby.podlobby.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "podcasts")
public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) unsigned")
    private long id;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "podcast")
    private List<Comment> comments;
    //////////////////////////////////////
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "podcast_categories",
            joinColumns = {@JoinColumn(name = "podcast_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories = new ArrayList<>();
    //////////////////////////////////////
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String embedLink;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column
    private Timestamp createdAt;

    public Podcast() { }

    public Podcast(long id, User user, List<Comment> comments, List<Category> categories, String title, String description, String embedLink, String image, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.comments = comments;
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.embedLink = embedLink;
        this.image = image;
        this.createdAt = createdAt;
    }

    public Podcast(User user, List<Comment> comments, List<Category> categories, String title, String description, String embedLink, String image, Timestamp createdAt) {
        this.user = user;
        this.comments = comments;
        this.categories = categories;
        this.title = title;
        this.description = description;
        this.embedLink = embedLink;
        this.image = image;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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

    public String getEmbedLink() {
        return embedLink;
    }

    public void setEmbedLink(String embedLink) {
        this.embedLink = embedLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
