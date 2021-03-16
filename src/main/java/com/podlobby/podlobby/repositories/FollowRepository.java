package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from users where id in (select follow_id from followed_users where user_id = ?1)")
    List<User> findAllByUserId(long id);

    @Query(nativeQuery = true, value = "select * from users where id in (select user_id from followed_users where follow_id = ?1)")
    List<User> findAllFollowersById(long id);
}
