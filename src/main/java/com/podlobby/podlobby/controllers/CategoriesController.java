package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoriesController {

    private final CategoryRepository categoryDao;
    private final UserService userService;

    public CategoriesController(CategoryRepository categoryDao, UserService userService){
        this.categoryDao = categoryDao;
        this.userService = userService;
    }

    @GetMapping("/admin/category-creation")
    public String createCategoryPage(){
        User user = userService.getLoggedInUser();
        if(user.getIsAdmin() < 1) return "redirect:/profile";
        return "admin/categories";
    }


    @PostMapping("/admin/category-creation")
    public String addCategoryToDB(@RequestParam(name = "category") String categoryName){
        Category newCategory = new Category();
        newCategory.setName(categoryName);
        categoryDao.save(newCategory);
        return "redirect:/profile";
    }

}
