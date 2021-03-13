package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class RecommendationController {

    private final CategoryRepository categoryDao;
    private final PodcastRepository podcastDao;
    private final UserService userService;


    public RecommendationController(CategoryRepository categoryDao, PodcastRepository podcastDao, UserService userService){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
        this.userService = userService;
    }

    @GetMapping("/getCategories")
    public String showModal(Model model){
        model.addAttribute("categoryList", categoryDao.findAll());
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return "recommendationsModal";
    }


    @GetMapping("/recommendations")
    public String getCategoryRecommendations(@RequestParam (name = "category") List<String> categoryList, Model model){
        System.out.println(categoryList);
        List<Podcast> podcastList = new ArrayList<>();
        for(String cat : categoryList){
            long catId = categoryDao.getIdByName(cat);
            podcastList.addAll(podcastDao.findAllByCategoryId(catId));
        }

        for(Podcast p : podcastList){
            System.out.println(p.getTitle());
        }

        // need to grab current user here and display the recommendations on their page based on this list
        return "redirect:/profile";
    }

}
