package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RecommendationController {

    private final CategoryRepository categoryDao;


    public RecommendationController(CategoryRepository categoryDao){
        this.categoryDao = categoryDao;
    }

    @GetMapping("/recommendations")
    public String showModal(Model model){
        model.addAttribute("categoryList", categoryDao.findAll());
        return "recommendationsModal";
    }

    @RequestMapping(value = "/recommendations/{categoryValues}", method = RequestMethod.POST)
    @ResponseBody
//    @PostMapping("/modal/{categoryValues}")
    public String getCategoryRecommendations(@PathVariable (name = "categoryValues") String categoryList){
        System.out.println(categoryList);
        // need to grab current user here and display the recommendations on their page based on this list
        return "users/profile";
    }

}
