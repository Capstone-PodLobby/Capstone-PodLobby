package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    //    Display login page   //
    @GetMapping("/login")
    public String showLoginForm(){

        // if a user is currently logged in
        User user = null;

        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            return "redirect:/profile";
        }
        return "users/login";
    }


}
