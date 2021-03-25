package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.Date;

@Controller
public class AboutUsController {

    @GetMapping("/about")
    public String showAboutUsPage(){
        return"about";
    }

}
