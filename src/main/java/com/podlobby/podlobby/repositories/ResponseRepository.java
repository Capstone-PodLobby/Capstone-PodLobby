package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Response;
import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    //Find all responses
    List<Response> findAll();

    Response findResponseByUser(User user);

    Response findResponsesByRequest(Request request);

    List<Response> findByUser(User user);
}
