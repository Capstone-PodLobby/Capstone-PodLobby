package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.model.Podcast;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class Settings {

    @GetMapping("/settings")
    public String settings(){
        return "users/settings";
    }

}
