package com.podlobby.podlobby.controllers;

import com.mailjet.client.errors.MailjetException;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Methods;
import org.aspectj.apache.bcel.classfile.Method;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final UserService userService;
    private final TLSEmail tlsEmail;
    private final Methods method = new Methods();

    public HomeController(UserService userService, TLSEmail tlsEmail){
        this.userService = userService;
        this.tlsEmail = tlsEmail;
    }

    //    Display the landing page (non-authenticated) //
    @GetMapping("/")
    public String showHomePage(HttpSession session){
        session.setAttribute("method", method);
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


    @PostMapping("/errorSubmit")
    public String errorSent(@RequestParam(name = "error-error") String error, @RequestParam(name = "error-timestamp") String time,
                            @RequestParam(name = "error-path") String path, @RequestParam(name = "error-status") String status,
                            @RequestParam(name = "error-message") String message, @RequestParam(name = "error-exception") String exception,
                            @RequestParam(name = "userMsg") String userMsg) {
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        String email = error + " at " + time + " path attempt: " + path + " status of : " + status + " message : " + message + " exception: " + exception + "/n" +
                " user message : " + userMsg;

        if(user != null) {
            tlsEmail.sendEmail("podlobby@gmail.com", user.getUsername(), "Site Error", email);
            return "redirect:/profile?errorSent";
        }
        tlsEmail.sendEmail("podlobby@gmail.com", "Visitor", "Site Error", email);
        return "redirect:/login?errorSent";
    }

}

