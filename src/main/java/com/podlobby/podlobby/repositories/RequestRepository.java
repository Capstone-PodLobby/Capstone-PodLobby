package com.podlobby.podlobby.repositories;

import com.podlobby.podlobby.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {


}
