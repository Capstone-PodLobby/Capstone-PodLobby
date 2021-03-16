package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Request;
import com.podlobby.podlobby.model.Response;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.RequestRepository;
import com.podlobby.podlobby.repositories.ResponseRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ResponseController {

=======
    private final UserService userService;
    private final RequestRepository requestDao;
    private final ResponseRepository responseDao;

    public ResponseController(ResponseRepository responseDao, RequestRepository requestDao, UserService userService){
        this.requestDao = requestDao;
        this.responseDao = responseDao;
        this.userService = userService;
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
    public String showResponses(Model model, User user, HttpServletRequest request){
        user = userService.getLoggedInUser();
        model.addAttribute("responseList", responseDao.findByUser(user));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "responses/user-responses";
    }
}
