package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.ResponseRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResponseController {

    private final ResponseRepository responseDao;
    private final UserService userService;

    public ResponseController(ResponseRepository responseDao, UserService userService){
        this.responseDao = responseDao;
        this.userService = userService;

    }

    @GetMapping("/user-responses")
    public String showResponses(Model model, User user, HttpServletRequest request){
        user = userService.getLoggedInUser();
        model.addAttribute("responseList", responseDao.findByUser(user));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "responses/user-responses";
    }
}
