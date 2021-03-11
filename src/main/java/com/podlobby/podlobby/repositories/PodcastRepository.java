package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {

    List<Podcast> findAll();

}
