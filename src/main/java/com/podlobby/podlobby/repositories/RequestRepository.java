package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAll();

    List<Request> findByUser(User user);

}
