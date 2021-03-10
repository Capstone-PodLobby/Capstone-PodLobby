package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    //    Display login page   //
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/modal")
    public String showModal(){
        return "testViewModal";
    }


}
