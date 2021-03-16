package com.podlobby.podlobby.controllers;

import com.mailjet.client.errors.MailjetException;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.UserService;
import org.springframework.mail.SimpleMailMessage;
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
    public String contactUs(Model model, HttpServletRequest request, @RequestParam(name = "currentUrl") String currentUrl, @RequestParam(name = "messageUs") String message) throws MailjetException {
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){

            model.addAttribute("currentUrl", request.getRequestURI());
            return "redirect:" + currentUrl + "?messageSent";// get the current page you are on
        }
        // create a random user for the email
        User emailSender = new User();
        emailSender.setEmail("help@podlobby.com");
        emailSender.setUsername("anonymous");

        return "redirect:/login?messageSent";
    }


}

