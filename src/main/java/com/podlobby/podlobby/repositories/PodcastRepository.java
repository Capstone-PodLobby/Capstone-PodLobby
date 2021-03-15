package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {

    List<Podcast> findAll();

//     change this to return a list of podcasts that fit that category id
    @Query(nativeQuery = true, value = "select * from podcasts where id in (select podcast_id from podcast_categories where category_id = ?1)")
    List<Podcast> findAllByCategoryId(long id);

    @Query(nativeQuery = true, value = "select * from podcasts where user_id = ?1")
    List<Podcast> findAllByUserId(long id);

    Podcast getByTitle(String title);

}
