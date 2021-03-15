package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService){
        this.userService = userService;
    }

    //    Display the landing page (non-authenticated) //
    @GetMapping("/")
    public String showHomePage(){
        return"home";
    }



    @PostMapping("/sendMessage")
    public String contactUs(Model model, HttpServletRequest request, @RequestParam(name = "currentUrl") String currentUrl){
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            model.addAttribute("currentUrl", request.getRequestURI());
            return "redirect:" + currentUrl + "?messageSent";// get the current page you are on
        }
        return "redirect:/login?messageSent";
    }

}

