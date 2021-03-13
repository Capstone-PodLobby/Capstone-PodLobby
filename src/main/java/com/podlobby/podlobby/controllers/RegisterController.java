package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.sql.Timestamp;


@Controller
public class RegisterController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;
    private final UserService userService;

    public RegisterController(UserRepository userDao, PasswordEncoder encoder, UserService userService) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.userService = userService;
    }

    //    Display registration page   //
    @GetMapping("/register")
    public String showRegisterForm(Model model){

        // if a user is currently logged in
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            return "redirect:/profile";
        }

        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String registered(Model model, @ModelAttribute User user, @RequestParam(name = "confirm-password", required = false) String confirmPassword){
        if(userDao.findByUsername(user.getUsername()) != null) {
            model.addAttribute("username", user.getUsername());
            return "redirect:/register?username";
        } else if(userDao.findByEmail(user.getEmail()) != null) {
            model.addAttribute("email", user.getEmail());
            return "redirect:/register?email";
        } else if(user.getAboutMe().isEmpty()) {
            model.addAttribute("about", user.getAboutMe());
            return "redirect:/register?about";
        } else if(!confirmPassword.equals(user.getPassword())) {
            model.addAttribute("mismatch", 0);
            return "redirect:/register?password";
        }

        user.setJoinedAt(new Timestamp(new Date().getTime()));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setBackgroundImage("https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1");

        userDao.save(user);
        return "redirect:/getCategories";
    }

}
