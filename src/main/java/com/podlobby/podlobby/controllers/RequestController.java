package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Methods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Controller
public class RequestController {

    private final RequestRepository requestDao;
    private final UserService userService;
    private final TLSEmail tlsEmail;


    public RequestController(RequestRepository requestDao, UserService userService, TLSEmail tlsEmail){
        this.requestDao = requestDao;
        this.userService = userService;
        this.tlsEmail = tlsEmail;

    }


    @GetMapping("/request")
    public String showRequestForm(Model model, HttpServletRequest request){
        model.addAttribute("request", new Request());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/request";
    }

    @PostMapping("/request")
    public String createRequest(@ModelAttribute Request request, @RequestParam(name = "req-amount") int amount, Model model, HttpServletRequest servletRequest){
        User user = userService.getLoggedInUser();
        System.out.println(amount);
        System.out.println(request.getGuestCount());
        request.setGuestCount(amount);
        if(request.getGuestCount() <= 0) {
            return "redirect:/request?guestCount";
        }
        request.setCreatedAt(new Timestamp(new Date().getTime()));
        request.setIsActive(1);
        request.setUser(user);
        requestDao.save(request);
        model.addAttribute("currentUrl", servletRequest.getRequestURI());

        List<Request> requestList = requestDao.findByUser(user);

        String message = "Thank you " + user.getUsername() + " for adding your " + Methods.numberSuffix(requestList.size()) + " request it can be found on your profile!";
        tlsEmail.sendEmail(user.getEmail(), user.getUsername(), "Your request has been added", message, false);

        return "redirect:/profile";
    }

    @GetMapping("/feeds/requests")
    public String showRequestPosts(Model model, HttpServletRequest request){
        model.addAttribute("requestList", requestDao.findAll());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "feeds/requests-feed";
    }

    @GetMapping("/user-requests")
    public String showRequestsAndResponses(Model model, HttpServletRequest request){
        User user = userService.getLoggedInUser();
        model.addAttribute("requestList", requestDao.findByUser(user));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/user-requests";
    }


}
