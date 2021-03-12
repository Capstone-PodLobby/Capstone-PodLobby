package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {



    @Query(nativeQuery = true, value = "select id from categories where name = ?1")
    long getIdByName(String name);

}
