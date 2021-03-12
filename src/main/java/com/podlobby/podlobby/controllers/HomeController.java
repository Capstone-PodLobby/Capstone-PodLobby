package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


//    Display the landing page (non-authenticated) //
    @GetMapping("/")
    public String showHomePage(){
        return"home";
    }

}

