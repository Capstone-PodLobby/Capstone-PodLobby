package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.Response;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import com.podlobby.podlobby.repositories.ResponseRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Methods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class RequestController {

    private final RequestRepository requestDao;
    private final UserService userService;
    private final TLSEmail tlsEmail;
    private final UserRepository userDao;
    private final ResponseRepository responseDao;


    public RequestController(RequestRepository requestDao, UserService userService, TLSEmail tlsEmail, UserRepository userDao, ResponseRepository responseDao){
        this.requestDao = requestDao;
        this.userService = userService;
        this.tlsEmail = tlsEmail;
        this.userDao = userDao;
        this.responseDao = responseDao;
    }


    // show the form
    @GetMapping("/request")
    public String showRequestForm(Model model, HttpServletRequest request, HttpSession session){
        model.addAttribute("request", new Request());
        model.addAttribute("newRequest", true);
        User user = userService.getLoggedInUser();
        session.setAttribute("user", user);
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/request";
    }

    // create the request
    @PostMapping("/request")
    public String createRequest(@ModelAttribute @Validated Request request, Errors validation, @RequestParam(name = "requestId") long id,
                                @RequestParam(name = "req-amount") int amount, Model model,
                                HttpServletRequest servletRequest){

        User user = userService.getLoggedInUser();
        List<String> errorMsg = new ArrayList<>();

        request.setGuestCount(amount);
        if(request.getGuestCount() <= 0) {
            validation.rejectValue("guestCount", "Guest count must be higher than 0");
            errorMsg.add("Guest count must be higher than 0");
        } else if (request.getDescription().isEmpty()) {
            validation.rejectValue("description", "Description can not be missing");
            errorMsg.add("Description can not be missing");
        } else if (request.getTitle().isEmpty()) {
            validation.rejectValue("title", "Title can not be missing");
            errorMsg.add("Title can not be missing");
        } else if (requestDao.findByTitle(request.getTitle()) != null) {
            validation.rejectValue("title", "Title can not be missing");
            errorMsg.add("Request title must be unique");
        }

        if(validation.hasErrors()) {
            model.addAttribute("request", request);
            model.addAttribute("errorList", errorMsg);
            model.addAttribute("user", user);
            model.addAttribute("currentUrl", servletRequest.getRequestURI());
            model.addAttribute("newRequest", true);
            return "requests/request";
        }

        request.setCreatedAt(new Timestamp(new Date().getTime()));
        request.setIsActive(1);
        request.setUser(user);
        requestDao.save(request);

        model.addAttribute("currentUrl", servletRequest.getRequestURI());

        List<Request> requestList = requestDao.findByUser(user);

        String message = "Thank you " + user.getUsername() + " for adding your " + Methods.numberSuffix(requestList.size()) + " request, it can be found on your profile!";
        tlsEmail.sendEmail(user.getEmail(), user.getUsername(), "Your request has been added", message);

        return "redirect:/profile?myPodcasts";
    }

    // view the feed of active requests
    @GetMapping("/feeds/requests")
    public String showRequestPosts(Model model, HttpServletRequest request, HttpSession session){
        model.addAttribute("requestList", requestDao.findAll());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        session.setAttribute("user", user);
        model.addAttribute("page", "Active Requests");
        model.addAttribute("currentUrl", request.getRequestURI());
        return "feeds/requests-feed";
    }

    // getting all requests for current user
    @GetMapping("/user-requests")
    public String showRequestsAndResponses(Model model, HttpServletRequest request, RedirectAttributes redirectAtr, HttpSession session){
        User user = userService.getLoggedInUser();
        session.setAttribute("user", user);
        List<Request> requestList = requestDao.findByUser(user);
        if(requestList.size() < 1) {
            redirectAtr.addFlashAttribute("message", "You do not have any active requests");
            return "redirect:/profile?myPodcasts";
        }

        model.addAttribute("requestList", requestList);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/user-requests";
    }

    // getting all the requests for the persons profile you are looking at
    @GetMapping("/user-requests/{id}")
    public String showRequestsForOtherUser(@PathVariable(name = "id") long id, Model model, HttpServletRequest request, RedirectAttributes redirectAtr){
        User user = userDao.getOne(id); // the user whos page you are on

        List<Request> requestList = requestDao.findByUser(user);
        if(requestList.size() < 1) {
            redirectAtr.addFlashAttribute("message", "This user does not have any active requests"); // would you like to be notified when they have created one ? ( notification feature )
            return "redirect:/otherProfile/" + id;
        }
        model.addAttribute("requestList", requestList);
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



    @GetMapping("/request/delete/{id}")
    public String deleteRequest (@PathVariable(name = "id") long id, RedirectAttributes redirectAtr){
        requestDao.deleteById(id); // need to cascade the responses for it
        redirectAtr.addFlashAttribute("message", "Your request has been deleted");
        return "redirect:/profile?myPodcasts";
    }

    @GetMapping("/request/edit/{id}")
    public String editRequest(@PathVariable(name = "id") long id, Model model, HttpServletRequest request){
        User user = userService.getLoggedInUser();

        if(requestDao.getOne(id).getUser().getId() != user.getId()){
            if(user.getIsAdmin() == 0) {
                return "redirect:/profile";
            }
        }

        model.addAttribute("editing", true);

        model.addAttribute("request", requestDao.getOne(id));
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "requests/request";
    }

    @PostMapping("/request/edit")
    public String editRequestPost(@RequestParam(name = "requestId") long id, Model model, @RequestParam(name = "req-amount") int amount,
                                  @ModelAttribute @Validated Request request, Errors validation, RedirectAttributes redirectAttributes, HttpServletRequest servletRequest) {

        List<String> errorMsg = new ArrayList<>();
        if (requestDao.findByTitle(request.getTitle()) != null && !request.getTitle().equals(requestDao.findById(id).getTitle())) {
            validation.rejectValue("title", "Title can not be missing");
            errorMsg.add("Request title must be unique");
        } else if(amount <= 0) {
            validation.rejectValue("guestCount", "Guest count must be higher than 0");
            errorMsg.add("Guest count must be higher than 0");
        }

        if(validation.hasErrors()){
            model.addAttribute("editing", true);
            model.addAttribute("errorList", errorMsg);
            model.addAttribute("request", requestDao.getOne(id));
            model.addAttribute("user", userService.getLoggedInUser());
            model.addAttribute("currentUrl", servletRequest.getRequestURI());
            return "requests/request";
        }

        Request updatingRequest = requestDao.getOne(id);
        updatingRequest.setCreatedAt(new Timestamp(new Date().getTime()));
        updatingRequest.setGuestCount(amount);
        updatingRequest.setDescription(request.getDescription());
        updatingRequest.setTitle(request.getTitle());
        requestDao.save(updatingRequest);

        redirectAttributes.addFlashAttribute("message", "Your request has been edited");
        return "redirect:/user-requests";
    }


}
