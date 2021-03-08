package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homePage {

    @GetMapping("/foot")
    public String goToHome(){
        return "testShow";
    }

}
