package com.podlobby.podlobby.controllers;

import com.mailjet.client.errors.MailjetException;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.sql.Timestamp;


@Controller
public class RegisterController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final TLSEmail tlsEmail;


    public RegisterController(UserRepository userDao, PasswordEncoder encoder, UserService userService, TLSEmail tlsEmail) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.userService = userService;
        this.tlsEmail = tlsEmail;
    }

    //    Display registration page   //
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpServletRequest request){

        // if a user is currently logged in
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            model.addAttribute("currentUrl", request.getRequestURI());
            return "redirect:/profile";
        }

        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String registered(Model model, @ModelAttribute User user, @RequestParam(name = "confirm-password", required = false) String confirmPassword,
                             @RequestParam(name = "g-recaptcha-response") String captcha) throws MailjetException, ServletException, IOException {
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
        } else if(captcha.isEmpty()){
            return "redirect:/register?captcha";
        }

        user.setJoinedAt(new Timestamp(new Date().getTime()));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setBackgroundImage("https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1");

        userDao.save(user);

        String emailContent = "Please follow this link to activate your account. http://localhost:8080/activate";
//        String emailContent = "Please follow this link to activate your account. podlobby.club/activate";
        tlsEmail.sendEmail(user.getEmail(), user.getUsername(), "Welcome to PodLobby", emailContent, false);
        return "redirect:/getCategories";
    }

    @GetMapping("/activate")
    public String activateAccount(){
        return "authentication/afterRegister";
    }

}
