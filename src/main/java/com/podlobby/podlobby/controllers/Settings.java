package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class Settings {

    private final UserService userService;

    public Settings(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String settings(Model model){
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return "users/settings";
    }

}
