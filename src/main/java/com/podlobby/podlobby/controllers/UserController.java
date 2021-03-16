package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CommentRepository;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final FollowRepository followDao;
    private final UserRepository userDao;
    private final PodcastRepository podcastDao;
    private final CommentRepository commentDao;

    public UserController(UserService userService, FollowRepository followDao, UserRepository userDao, PodcastRepository podcastDao, CommentRepository commentDao){
        this.userService = userService;
        this.followDao = followDao;
        this.userDao = userDao;
        this.podcastDao = podcastDao;
        this.commentDao = commentDao;
    }


//    @PathVariable long id,
    @GetMapping("/profile")
    public String profilePage(Model model, HttpSession session, HttpServletRequest request){
//        get the current user
        User user = userService.getLoggedInUser();
        int followingCount = followDao.findAllByUserId(user.getId()).size();
        List<Podcast> createdPodcasts = user.getPodcasts();

        session.setAttribute("user", user);
        model.addAttribute("userController", userDao); // used in comment modal

//        model.addAttribute("podcast",podcastDao.getOne(id));


        model.addAttribute("followingCount", followingCount); // make this a session attribute ? will it update on each page ( needs to be tested )
        model.addAttribute("userPodcasts", createdPodcasts); // ^^ same
        model.addAttribute("currentUrl", request.getRequestURI());
        return "users/profile";
    }



    //clicking on a podcast to see the creator's profile page
    @GetMapping("/otherProfile/{id}")
    public String viewFollowersProfile(Model model, HttpServletRequest request, @PathVariable(name = "id") long id){

        model.addAttribute("currentUrl", request.getRequestURI());
        User currUser = userService.getLoggedInUser();
        long currUserId = currUser.getId();
        // this is your podcast
        if(currUserId == id){
            return "redirect:/profile";
        }

        model.addAttribute("user", currUser);
        User following = userDao.findById(id).get();
        model.addAttribute("following", following);

        List<Podcast> createdPodcasts = podcastDao.findAllByUserId(id);

        model.addAttribute("followingPodcasts", createdPodcasts);
        model.addAttribute("userControllerId", following);


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

        return "users/othersProfile";
    }

    @GetMapping("/otherProfile/delete/{id}")
    public String deleteUser(Model model, @PathVariable(name = "id") long id, HttpServletRequest request, @RequestParam(name = "currentUrl") String currentUrl){
        userDao.delete(userDao.getOne(id));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/profile?deleted";
    }

}
