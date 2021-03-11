package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class RequestController {

    private final RequestRepository requestDao;


    public RequestController(RequestRepository requestDao){
        this.requestDao = requestDao;

    }


    @GetMapping("/request")
    public String showRequestForm(Model model){
        model.addAttribute("request", new Request());
        return "request";
    }

    @PostMapping("/request")
    public String createRequest(@ModelAttribute Request request){
        requestDao.save(request);
        return "redirect:/profile";
    }

    @GetMapping("/feeds/requests")
    public String showRequestPosts(Model model){
        model.addAttribute("requestList", requestDao.findAll());
        return "feeds/requests-feed";
    }




}
