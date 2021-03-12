package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
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


    public RecommendationController(CategoryRepository categoryDao, PodcastRepository podcastDao){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
    }

    @GetMapping("/getCategories")
    public String showModal(Model model){
        model.addAttribute("categoryList", categoryDao.findAll());
        return "recommendationsModal";
    }

//    @RequestMapping(value = "/recommendations}", method = RequestMethod.GET)
//    @ResponseBody
//    @PostMapping("/modal/{categoryValues}")
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
