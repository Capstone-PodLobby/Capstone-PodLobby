package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.Response;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.*;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.model.Request;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final FollowRepository followDao;
    private final UserRepository userDao;
    private final PodcastRepository podcastDao;
    private final CommentRepository commentDao;
    private final ResponseRepository responseDao;

    public UserController(UserService userService, ResponseRepository responseDao, FollowRepository followDao, UserRepository userDao, PodcastRepository podcastDao, CommentRepository commentDao){
        this.userService = userService;
        this.followDao = followDao;
        this.userDao = userDao;
        this.podcastDao = podcastDao;
        this.commentDao = commentDao;
        this.responseDao = responseDao;
    }


//    @PathVariable long id,
    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session, HttpServletRequest request){
//        get the current user
        User user = userService.getLoggedInUser();
        int followingCount = followDao.findAllByUserId(user.getId()).size();
        int numberOfFollowers = followDao.findAllFollowersById(user.getId()).size();
        List<Podcast> createdPodcasts = user.getPodcasts();

        session.setAttribute("user", user);
        model.addAttribute("userController", userDao); // used in comment modal

        model.addAttribute("followsCount", numberOfFollowers); //will display the number of followers a user has


//        model.addAttribute("podcast",podcastDao.getOne(id));



        model.addAttribute("followingCount", followingCount); // make this a session attribute ? will it update on each page ( needs to be tested )
        model.addAttribute("userPodcasts", createdPodcasts); // ^^ same
        model.addAttribute("currentUrl", request.getRequestURI());
        return "users/profile";
    }



    //clicking on a podcast to see the creator's profile page
    @GetMapping("/otherProfile/{id}")
    public String viewFollowersProfile(Model model, HttpServletRequest request, @PathVariable(name = "id") long id, HttpSession session){

        model.addAttribute("currentUrl", request.getRequestURI());
        User currUser = userService.getLoggedInUser();
        long currUserId = currUser.getId();
        // this is your podcast
        if(currUserId == id){
            return "redirect:/profile";
        }

        model.addAttribute("user", currUser);
        User following = userDao.getOne(id);
        model.addAttribute("following", following);

        List<Podcast> createdPodcasts = podcastDao.findAllByUserId(id);

        model.addAttribute("followingPodcasts", createdPodcasts);

        // check if this person is someone i am already following
        List<User> followedUsers = followDao.findAllByUserId(currUserId);
        boolean alreadyFollowing = false;
        for(User user : followedUsers){
            long userId = user.getId();
            if (userId == id) {
                alreadyFollowing = true;
                break;
            }
        }

        model.addAttribute("isFollowing", alreadyFollowing);
        model.addAttribute("userController", userDao);

        // get the responses you have made for this users request you are viewing
        Request requestPost = null;
        // if you came this route through the request button
        try {
            requestPost = (Request) session.getAttribute("request");
        } catch (Exception ignored){}

        if(requestPost != null) {
            List<Response> allResponses = responseDao.findAllByRequestId(requestPost.getId());
            List<Response> yourResponses = new ArrayList<>();
            for (Response r : allResponses) {
                if (r.getUser().getId() == currUserId) {
                    yourResponses.add(r);
                }
            }
            model.addAttribute("responseList", yourResponses);
        }

        return "users/othersProfile";
    }

}
