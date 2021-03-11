package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.repositories.RequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RequestController {

    private final RequestRepository requestDao;

    public RequestController(RequestRepository requestDao){
        this.requestDao = requestDao;
    }

    //    Display request page   //
    @GetMapping("/request")
    public String showRegisterForm(){
        return "request";
    }

    @GetMapping("/feeds/requests")
    public String showRequestPosts(Model model){
        model.addAttribute("requestList", requestDao.findAll());
        return "feeds/requests-feed";
    }




}
