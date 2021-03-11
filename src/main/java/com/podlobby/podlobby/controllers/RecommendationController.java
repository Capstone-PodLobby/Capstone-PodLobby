package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RecommendationController {

    private final CategoryRepository categoryDao;


    public RecommendationController(CategoryRepository categoryDao){
        this.categoryDao = categoryDao;
    }

    @GetMapping("/modal")
    public String showModal(Model model){
        model.addAttribute("categoryList", categoryDao.findAll());
        return "testViewModal";
    }

    @PostMapping("/modal")
    public String getCategoryRecommendations(){
        return "users/profile";
    }

}
