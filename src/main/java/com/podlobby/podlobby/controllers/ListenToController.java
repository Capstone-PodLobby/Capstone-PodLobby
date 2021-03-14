package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String listenToAPodcast(@PathVariable(name = "id") long id, Model model, HttpSession session){
        Podcast wantToListenTo = podcastDao.getOne(id);
        System.out.println("++++++++++++++++");
        System.out.println(wantToListenTo.getTitle());
        System.out.println(wantToListenTo.getEmbedLink());
        System.out.println("++++++++++++++++");
        model.addAttribute("user", userService.getLoggedInUser());
        session.setAttribute("listeningTo", wantToListenTo.getEmbedLink());
        return "redirect:/profile?myPodcasts"; // current page you are on
        // ^^ this is temporary
    }

}
