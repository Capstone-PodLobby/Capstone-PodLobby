package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RequestController {
    //    Display request page   //
    @GetMapping("/request")
    public String showRegisterForm(){
        return "request";
    }




}
