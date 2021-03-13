package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;


@Controller
public class PodcastPostController {

    private final CategoryRepository categoryDao;
    private final PodcastRepository podcastDao;
    private final UserRepository userDao;
    private final UserService userService;

    public PodcastPostController(CategoryRepository categoryDao, UserService userService, PodcastRepository podcastDao, UserRepository userDao){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
        this.userDao = userDao;
        this.userService = userService;
    }

    /////////////////
    //EDIT   OPTION//
    /////////////////
    //Will need to add ID to path for specific post
    @GetMapping("/podcasts/edit")
    public String viewEditPodcastForm(Model model) {
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
//        model.addAttribute("post", postsDao.getOne(id));
        return "podcasts/edit";
    }

    @PostMapping("/podcast/{id}/edit")
    public String editPodcast(Model model, @PathVariable(name = "id") long id){

        return "users/profile";
    }


    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/podcasts/create")
    public String showPodcastCreate(Model model){
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("podcast", new Podcast());
        model.addAttribute("categoryList", categoryDao.findAll());
        return"/podcasts/create";
    }



    @PostMapping("/podcasts/create")
    public String createPodcast(Model model, @ModelAttribute Podcast podcast, @RequestParam(name = "categories", required = false) String categories) {
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);

        if(podcast.getTitle().isEmpty()) {
            return "redirect:/podcasts/create?title";
        } else if(podcast.getDescription().isEmpty()) {
            return "redirect:/podcasts/create?description";
        } else if(podcast.getEmbedLink().isEmpty()) {
            return "redirect:/podcasts/create?embed";
        } else if(podcast.getCategories().isEmpty()) {
            return "redirect:/podcasts/create?categories";
        }

        if(podcast.getImage().isEmpty()){
            podcast.setImage("https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1");
        }

        List<Category> categoryList = new ArrayList<>();
        String[] catList = categories.split(",");

        for(String c : catList) {
            Long catId = Long.parseLong(c) + 1;
            categoryList.add(categoryDao.getOne(catId));
        }

        podcast.setCategories(categoryList);

        for(Category c : podcast.getCategories()) {
            System.out.println(c.getName() + " was added to " + podcast.getTitle());
        }

        podcast.setCreatedAt(new Timestamp(new Date().getTime()));
        podcast.setUser(userDao.getOne(1L)); // ----- GET LOGGED IN USER
        podcastDao.save(podcast);
        return "users/profile";
    }


}
