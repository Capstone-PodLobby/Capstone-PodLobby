package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final FollowRepository followDao;
    private final UserRepository userDao;
    private final PodcastRepository podcastDao;
    private final IframeParser iframeParser = new IframeParser();

    public UserController(UserService userService, FollowRepository followDao, UserRepository userDao, PodcastRepository podcastDao){
        this.userService = userService;
        this.followDao = followDao;
        this.userDao = userDao;
        this.podcastDao = podcastDao;
    }

    @GetMapping("/profile")
    public String profilePage(Model model){
//        get the current user
        User user = userService.getLoggedInUser();
        int followerCount = followDao.findAllByUserId(user.getId()).size();
        List<Podcast> createdPodcasts = user.getPodcasts();
        for(Podcast p : createdPodcasts){
            String parsedEmbedLink = iframeParser.parseIframe(p.getEmbedLink());
            p.setEmbedLink(parsedEmbedLink);
        }
        model.addAttribute("user", user);
        model.addAttribute("followerCount", followerCount);
        model.addAttribute("userPodcasts", createdPodcasts);
        return "users/profile";
    }



    //clicking on a podcast to see the creator's profile page
    @GetMapping("/otherProfile/{id}")
    public String viewFollowersProfile(Model model, @PathVariable(name = "id") long id){

        User currUser = userService.getLoggedInUser();
        long currUserId = currUser.getId();
        // this is your podcast
        if(currUserId == id){
            return "redirect:/profile";
        }

        model.addAttribute("user", currUser);
        User follower = userDao.findById(id).get();
        model.addAttribute("follower", follower);

        List<Podcast> createdPodcasts = podcastDao.findAllByUserId(id);
        for(Podcast p : createdPodcasts){
            String parsedEmbedLink = iframeParser.parseIframe(p.getEmbedLink());
            p.setEmbedLink(parsedEmbedLink);
        }
        model.addAttribute("followerPodcasts", createdPodcasts);

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
        return "othersProfile";
    }
}
