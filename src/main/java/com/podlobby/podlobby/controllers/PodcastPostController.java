package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;


@Controller
public class PodcastPostController {

    private final CategoryRepository categoryDao;
    private final PodcastRepository podcastDao;
    private final UserRepository userDao;

    public PodcastPostController(CategoryRepository categoryDao, PodcastRepository podcastDao, UserRepository userDao){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
        this.userDao = userDao;
    }

    /////////////////
    //EDIT   OPTION//
    /////////////////
    //Will need to add ID to path for specific post
    @GetMapping("/podcasts/edit")
    public String viewEditPodcastForm() {
//        model.addAttribute("post", postsDao.getOne(id));
        return "podcasts/edit";
    }


    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/podcasts/create")
    public String showPodcastCreate(Model model){
        model.addAttribute("podcast", new Podcast());
        model.addAttribute("categoryList", categoryDao.findAll());
        return"/podcasts/create";
    }



    @PostMapping("/podcasts/create")
    public String createPodcast(@ModelAttribute Podcast podcast, @RequestParam(name = "categories", required = false) String categories) {

        if(podcast.getTitle().isEmpty()) {
            return "redirect:/podcasts/create?title";
        } else if(podcast.getDescription().isEmpty()) {
            return "redirect:/podcasts/create?description";
        } else if(podcast.getEmbedLink().isEmpty()) {
            return "redirect:/podcasts/create?embed";
        } else if(podcast.getCategories().isEmpty()) {
            return "redirect:/podcasts/create?categories";
        }

        List<Category> categoryList = new ArrayList<>();
        String[] catList = categories.split(",");

        for(String c : catList) {
            Long catId = Long.parseLong(c) + 1;
            categoryList.add(categoryDao.getOne(catId));
        }

        podcast.setCategories(categoryList);

//        System.out.println(podcast.getTitle());
//        System.out.println(podcast.getDescription());
//        System.out.println(podcast.getEmbedLink());
//        System.out.println(podcast.getImage());

        for(Category c : podcast.getCategories()) {
            System.out.println(c.getName() + " was added to " + podcast.getTitle());
        }

        podcast.setCreatedAt(new Timestamp(new Date().getTime()));
        podcast.setUser(userDao.getOne(1L)); // ----- GET LOGGED IN USER
        podcastDao.save(podcast);
        return "users/profile";
    }


}
