package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import com.podlobby.podlobby.util.Methods;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {

    private final UserService userService;
    private final PodcastRepository podcastDao;
    private final UserRepository usersDao;
    private final FollowRepository followDao;
    private final Methods methods = new Methods();

    public FeedController(PodcastRepository podcastDao, UserRepository usersDao, FollowRepository followDao, UserService userService){
        this.podcastDao = podcastDao;
        this.usersDao = usersDao;
        this.followDao = followDao;
        this.userService = userService;
    }

    @GetMapping("/feeds/global")
    public String showGlobalFeed(Model model, HttpServletRequest request, HttpSession session){
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}
        if(user != null) {
            session.setAttribute("user", user);
        }
        List<Podcast> podcasts = podcastDao.findAll();
        model.addAttribute("page", "Global Feed");
        model.addAttribute("podcasts", podcasts);
        model.addAttribute("currentUrl", request.getRequestURI());
        session.setAttribute("method", methods);
        return"feeds/global-feed";
    }


    @GetMapping("/feeds/filtered")
    public String showFilteredFeed(Model model, HttpServletRequest request, HttpSession session){
        User user = userService.getLoggedInUser();
        session.setAttribute("user", user);
        model.addAttribute("page", "Follower Feed");
        model.addAttribute("currentUrl", request.getRequestURI());
        List<Podcast> selectPodcast = new ArrayList<>();
        List<User> following = followDao.findAllByUserId(user.getId());

        for(User member : following) {
           selectPodcast.addAll(member.getPodcasts());
        }

        model.addAttribute("filtered", selectPodcast);
        return"feeds/filtered-feed";
    }




}
