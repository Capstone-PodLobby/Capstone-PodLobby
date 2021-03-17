package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.Response;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import com.podlobby.podlobby.repositories.ResponseRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ResponseController {

    private final UserService userService;
    private final RequestRepository requestDao;
    private final ResponseRepository responseDao;
    private final UserRepository userDao;
    private final TLSEmail tlsEmail;

    public ResponseController(ResponseRepository responseDao, RequestRepository requestDao, UserService userService, UserRepository userDao, TLSEmail tlsEmail){
        this.requestDao = requestDao;
        this.responseDao = responseDao;
        this.userService = userService;
        this.userDao = userDao;
        this.tlsEmail = tlsEmail;
    }    

    @PostMapping("/response/create/{title}")
    public String createResponse(@PathVariable(name = "title") String title, @RequestParam(name = "response") String responseContent,
                                 @RequestParam(name = "currentUrl") String currentUrl){
        User user = userService.getLoggedInUser();
        // get the request you are adding a response to
        Request thisRequest = requestDao.findByTitle(title);
        // get all of this request's current responses
        List<Response> responseList = responseDao.findAllByRequestId(thisRequest.getId());
        // create new response
        Response response = new Response();
        response.setCreatedAt(new Timestamp(new Date().getTime()));
        response.setUser(user);
        response.setContent(responseContent);
        response.setRequest(thisRequest);
        response.setAcceptedStatus(0);
        responseDao.save(response);

        // add it to the request list
        responseList.add(response);
        thisRequest.setResponseList(responseList);
        requestDao.save(thisRequest);
        return "redirect:" + currentUrl;
    }

    @GetMapping("/response/delete/{id}")
    public String deleteResponse(@PathVariable(name = "id") long id, @RequestParam(name = "currentUrl") String currentUrl){
        Request thisRequest = requestDao.findById(responseDao.getOne(id).getRequest().getId());
        List<Response> responseList = responseDao.findAllByRequestId(thisRequest.getId());
        responseList.remove(responseDao.getOne(id));
        thisRequest.setResponseList(responseList);
        requestDao.save(thisRequest);
        responseDao.deleteById(id);
        return "redirect:" + currentUrl;
    }

    @GetMapping("/user-responses")
    public String showResponses(Model model, HttpServletRequest request){
        User user = userService.getLoggedInUser();
        model.addAttribute("responseList", responseDao.findByUser(user));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "responses/user-responses";
    }

    @GetMapping("/accepted/{id}")
    public String acceptedResponse(@PathVariable(name = "id") long id, RedirectAttributes redirectAtr){
        Response thisResponse = responseDao.getOne(id);
        Request thisRequest = requestDao.findById(thisResponse.getRequest().getId());
        int guestCount = thisRequest.getGuestCount();
        if(guestCount > 0) {
            guestCount--;
            thisRequest.setGuestCount(guestCount);
            requestDao.save(thisRequest);
            thisResponse.setAcceptedStatus(1);
            responseDao.save(thisResponse);
            return "redirect:/user-requests";
        }
        redirectAtr.addFlashAttribute("message", "Your request does not need any more guests");
        return "redirect:/user-requests";
    }

    @GetMapping("/collaborate/{id}")
    public String showCollaborateForm(@PathVariable(name = "id") long id, Model model){
        User userToContact = userDao.getOne(id); // the responder or the requester
        model.addAttribute("userToContact", userToContact);
        return "users/collaborate";
    }

    @PostMapping("/collaborate/contact")
    public String contactRequester(@RequestParam(name = "body") String messageBody, @RequestParam(name = "id") long toUserId){
        // get the user you are sending a message to
        User toUser = userDao.getOne(toUserId);
        // send email message
        tlsEmail.sendEmail(toUser.getEmail(), toUser.getUsername(), "Let's Collaborate on a Podcast!", messageBody, false);
        return "redirect:/profile";
    }

}
