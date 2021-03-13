package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    //    Display the landing page (non-authenticated) //
    @GetMapping("/")
    public String showHomePage(){
        return"home";
    }

    @PostMapping("/sendMessage")
    public String contactUs(){
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            return "redirect:/profile?messageSent";
        }
        return "redirect:/login?messageSent";
    }

}

