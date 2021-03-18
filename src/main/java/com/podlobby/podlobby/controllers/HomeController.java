package com.podlobby.podlobby.controllers;

import com.mailjet.client.errors.MailjetException;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.TLSEmail;
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
    private final TLSEmail tlsEmail;

    public HomeController(UserService userService, TLSEmail tlsEmail){
        this.userService = userService;
        this.tlsEmail = tlsEmail;
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
            tlsEmail.sendEmail("podlobby@gmail.com", user.getUsername(), "Help request", message);
            model.addAttribute("currentUrl", request.getRequestURI());
            return "redirect:" + currentUrl + "?messageSent";// get the current page you are on
        }

        tlsEmail.sendEmail("podlobby@gmail.com", "Visitor", "Help request", message);

        return "redirect:/login?messageSent";
    }


}

