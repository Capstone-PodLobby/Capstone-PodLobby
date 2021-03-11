package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
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

    public RegisterController(UserRepository userDao) {
        this.userDao = userDao;
    }

    //    Display registration page   //
    @GetMapping("/register")
    public String showRegisterForm(Model model){
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
        System.out.println("================");
        System.out.println(confirmPassword);
        user.setJoinedAt(new Timestamp(new Date().getTime()));
        userDao.save(user);
        return "redirect:/login";
}
}
