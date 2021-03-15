package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.CommentRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;


@Controller
public class PodcastPostController {

    private final CategoryRepository categoryDao;
    private final PodcastRepository podcastDao;
    private final UserRepository userDao;
    private final CommentRepository commentDao;
    private final UserService userService;

    public PodcastPostController(CategoryRepository categoryDao, CommentRepository commentDao, UserService userService, PodcastRepository podcastDao, UserRepository userDao){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
        this.userDao = userDao;
        this.userService = userService;
        this.commentDao = commentDao;
    }

    /////////////////
    //EDIT   OPTION//
    /////////////////
    //Will need to add ID to path for specific post
    @GetMapping("/podcasts/edit")
    public String viewEditPodcastForm(Model model, HttpServletRequest request) {
//        model.addAttribute("podcast", podcastDao.getOne(id));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "podcasts/edit";
    }

    @PostMapping("/podcast/{id}/edit")
    public String editPodcast(Model model, @PathVariable(name = "id") long id, HttpServletRequest request){
        model.addAttribute("currentUrl", request.getRequestURI());
        return "users/profile";
    }

    @GetMapping("/podcast/delete/{id}")
    public String deletePodcast(Model model, @PathVariable(name = "id") long id, HttpServletRequest request, @RequestParam(name = "currentUrl") String currentUrl){
        // cascade all is in the podcast model but podcast delete errors out. comments and podcast category id needs to be removed first
        podcastDao.delete(podcastDao.getOne(id));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/profile?deleted";
    }


    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/podcasts/create")
    public String showPodcastCreate(Model model, HttpServletRequest request){
        model.addAttribute("podcast", new Podcast());
        model.addAttribute("categoryList", categoryDao.findAll());
        model.addAttribute("currentUrl", request.getRequestURI());
        return"podcasts/create";
    }



    @PostMapping("/podcasts/create")
    public String createPodcast(Model model, @ModelAttribute Podcast podcast, @RequestParam(name = "categories", required = false) String categories, HttpServletRequest request) {

        IframeParser iframeParser = new IframeParser(); // to parse it on creation

        if (podcast.getTitle().isEmpty()) {
            return "redirect:/podcasts/create?title";
        } else if (podcast.getDescription().isEmpty()) {
            return "redirect:/podcasts/create?description";
        } else if (podcast.getEmbedLink().isEmpty()) {
            return "redirect:/podcasts/create?embed";
        } else if (podcast.getCategories().isEmpty()) {
            return "redirect:/podcasts/create?categories";
        } else if (iframeParser.parseIframe(podcast.getEmbedLink()).equalsIgnoreCase("nosrc")){
            return "redirect:/podcasts/create?embedIssue";
        }

        if(podcast.getImage().isEmpty()){ // set the default image if one is not provided
            podcast.setImage("https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1");
        }

        // give the categories to the podcast
        List<Category> categoryList = new ArrayList<>();
        String[] catList = categories.split(",");

        for(String c : catList) {
            Long catId = Long.parseLong(c) + 1;
            categoryList.add(categoryDao.getOne(catId));
        }

        podcast.setCategories(categoryList);


        podcast.setCreatedAt(new Timestamp(new Date().getTime()));
        podcast.setUser(userDao.getOne(userService.getLoggedInUser().getId())); // ----- GET LOGGED IN USER -> session ?
        podcast.setEmbedLink(iframeParser.parseIframe(podcast.getEmbedLink())); // parse it before it is stored in the database
        podcastDao.save(podcast);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/profile";
    }


}
