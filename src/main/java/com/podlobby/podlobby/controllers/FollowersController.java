package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FollowersController {

    private final FollowRepository followDao;
    private final UserRepository userDao;

    public FollowersController(FollowRepository followDao, UserRepository userDao){
        this.followDao = followDao;
        this.userDao = userDao;
    }

    //Will need to ad ID for specific followers on page
    @GetMapping("/followers")
    public String showFollowers(Model model){
        model.addAttribute("followList", followDao.findAllByUserId(1L));
        return"followers";
    }


    @GetMapping("/followers/{id}")
    public String viewFollowersProfile(Model model, @PathVariable(name = "id") long id){
        model.addAttribute("follower", userDao.findById(id)); // maybe make a method in the userDao that only selects public information?
        return "followers";
    }

}
