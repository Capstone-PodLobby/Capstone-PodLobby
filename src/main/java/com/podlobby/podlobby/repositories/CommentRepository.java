package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
