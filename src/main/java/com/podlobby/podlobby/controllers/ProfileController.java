package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/user-profile")
    public String profilePage(){
        return "othersProfile";
    }
}
