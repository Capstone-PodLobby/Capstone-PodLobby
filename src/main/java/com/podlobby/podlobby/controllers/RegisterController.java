package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RegisterController {
    //    Display registration page   //
    @GetMapping("/register")
    public String showRegisterForm(){
        return "register";
    }

//   Will add a post mapping for registration later  //


}
