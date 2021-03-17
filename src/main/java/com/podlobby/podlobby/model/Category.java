package com.podlobby.podlobby.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) unsigned")
    private long id;

    @Column(nullable = false)
    private String name;
//////////////////////////////////////
//    Adding cascadetype.all to this fixed our foreign key error
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    private List<Podcast> podcastList = new ArrayList<>();
//////////////////////////////////////
    public Category(){}

    public Category(long id, String name, List<Podcast> podcastList) {
        this.id = id;
        this.name = name;
        this.podcastList = podcastList;
    }

    public Category(String name, List<Podcast> podcastList) {
        this.name = name;
        this.podcastList = podcastList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Podcast> getPodcastList() {
        return podcastList;
    }

    public void setPodcastList(List<Podcast> podcastList) {
        this.podcastList = podcastList;
    }
}
