package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Comment;
import com.podlobby.podlobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
