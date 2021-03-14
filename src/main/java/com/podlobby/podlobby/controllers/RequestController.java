package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.util.Date;


@Controller
public class RequestController {

    private final RequestRepository requestDao;
    private final UserService userService;


    public RequestController(RequestRepository requestDao, UserService userService){
        this.requestDao = requestDao;
        this.userService = userService;

    }


    @GetMapping("/request")
    public String showRequestForm(Model model){
        model.addAttribute("request", new Request());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return "requests/request";
    }

    @PostMapping("/request")
    public String createRequest(@ModelAttribute Request request){
        User user = userService.getLoggedInUser();
        request.setCreatedAt(new Timestamp(new Date().getTime()));
        request.setIsActive(1);
        request.setUser(user);
        requestDao.save(request);
        return "redirect:/profile";
    }

    @GetMapping("/feeds/requests")
    public String showRequestPosts(Model model){
        model.addAttribute("requestList", requestDao.findAll());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return "feeds/requests-feed";
    }

    @GetMapping("/user-requests")
    public String showRequestsAndResponses(Model model, User user){
        user = userService.getLoggedInUser();
        model.addAttribute("requestList", requestDao.findByUser(user));
        return "requests/user-requests";
    }


}
