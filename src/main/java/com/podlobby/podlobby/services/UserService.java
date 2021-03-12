package com.podlobby.podlobby.services;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Security;

@Service
public class UserService {
    private final UserRepository usersDao;

    public UserService(UserRepository usersDao){this.usersDao = usersDao;}

    public User getLoggedInUser(){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usersDao.findById(loggedInUser.getId()).get();
    }
}
