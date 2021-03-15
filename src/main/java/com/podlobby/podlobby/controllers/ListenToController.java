package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ListenToController {

    private final PodcastRepository podcastDao;
    private final UserService userService;

    public ListenToController(PodcastRepository podcastDao, UserService userService){
        this.podcastDao = podcastDao;
        this.userService = userService;
    }

    @GetMapping("/listen/{id}")
    public String listenToAPodcast(@PathVariable(name = "id") long id, Model model, HttpSession session, @RequestParam(name = "currentUrl") String currentUrl){
        Podcast wantToListenTo = podcastDao.getOne(id);
        model.addAttribute("user", userService.getLoggedInUser());
        session.setAttribute("listeningTo", wantToListenTo.getEmbedLink());
        return "redirect:" + currentUrl; // current page you are on
    }

}
