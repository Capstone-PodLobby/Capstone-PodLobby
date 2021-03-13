package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ListenToController {

    private final PodcastRepository podcastDao;
    private final UserService userService;

    public ListenToController(PodcastRepository podcastDao, UserService userService){
        this.podcastDao = podcastDao;
        this.userService = userService;
    }

    @GetMapping("/listen/{id}")
    public String listenToAPodcast(@PathVariable(name = "id") long id, Model model){
        Podcast wantToListenTo = podcastDao.getOne(id);
        // need to stay on the page that you are currently on just add this specific podcast to the navbar to be played
        // will error out right now until ^^ is added
        model.addAttribute("user", userService.getLoggedInUser());
        return "users/profile";
    }

}
