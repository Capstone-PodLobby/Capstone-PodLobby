package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Methods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Controller
public class RequestController {

    private final RequestRepository requestDao;
    private final UserService userService;
    private final TLSEmail tlsEmail;
    private final UserRepository userDao;


    public RequestController(RequestRepository requestDao, UserService userService, TLSEmail tlsEmail, UserRepository userDao){
        this.requestDao = requestDao;
        this.userService = userService;
        this.tlsEmail = tlsEmail;
        this.userDao = userDao;
    }


    // show the form
    @GetMapping("/request")
    public String showRequestForm(Model model, HttpServletRequest request){
        model.addAttribute("request", new Request());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/request";
    }

    // create the request
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

    // view the feed of active requests
    @GetMapping("/feeds/requests")
    public String showRequestPosts(Model model, HttpServletRequest request){
        model.addAttribute("requestList", requestDao.findAll());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "feeds/requests-feed";
    }

    // getting all requests for a specific user
    @GetMapping("/user-requests")
    public String showRequestsAndResponses(Model model, HttpServletRequest request){
        User user = userService.getLoggedInUser();
        model.addAttribute("requestList", requestDao.findByUser(user));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/user-requests";
    }

    // getting all the requests for the persons profile you are looking at
    @GetMapping("/user-requests/{id}")
    public String showRequestsForOtherUser(@PathVariable(name = "id") long id, Model model, HttpServletRequest request){
        User user = userDao.getOne(id); // the user whos page you are on
        model.addAttribute("requestList", requestDao.findByUser(user));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/user-requests";
    }

    // clicking on the button for the request
    @GetMapping("/user-request/{id}")
    public String showProfileForRequester(@PathVariable(name = "id") long id, @RequestParam(name = "currentUrl") String currentUrl, Model model, HttpSession session){
        User user = userService.getLoggedInUser(); // get the logged in user
        Request request = requestDao.getOne(id); // get the request that you clicked on
        User requester = request.getUser(); // get the user who made the request
        model.addAttribute("currentUrl", currentUrl);
        if(requester.getId() == user.getId()){        // if it is yours -> go to profile
            return "redirect:/profile?myPodcasts";
        }
        // take you to the user's profile that made the request
        session.setAttribute("request", request);
        return "redirect:/otherProfile/" + requester.getId() + "?requestViewing"; // go to that user's page so you can work with them
    }



}


// request.getSession().putKey("infoMessage", "whatever")