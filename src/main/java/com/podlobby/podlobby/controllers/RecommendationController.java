package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class RecommendationController {

    private final CategoryRepository categoryDao;
    private final PodcastRepository podcastDao;
    private final FollowRepository followDao;
    private final UserRepository userDao;
    private final UserService userService;

    public RecommendationController(CategoryRepository categoryDao, FollowRepository followDao, PodcastRepository podcastDao, UserRepository userDao, UserService userService){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
        this.userService = userService;
        this.userDao = userDao;
        this.followDao = followDao;
    }

    @GetMapping("/getCategories")
    public String showModal(Model model, HttpSession session, HttpServletRequest request){
        model.addAttribute("categoryList", categoryDao.findAll());
        session.setAttribute("user", userService.getLoggedInUser()); // needs to be set if you are taken here upon registration
        model.addAttribute("currentUrl", request.getRequestURI());
        return "users/recommendationsModal";
    }


    @GetMapping("/recommendations")
    public String getCategoryRecommendations(@RequestParam (name = "category") List<String> categoryList,
                                             Model model, HttpSession session, HttpServletRequest request,
                                             RedirectAttributes redirectAttributes){
        User user = (User) session.getAttribute("user");
        int followingCount = followDao.findAllByUserId(user.getId()).size();
        model.addAttribute("followingCount", followingCount); // set this on the session ?

        // if nothing was chosen
        if(categoryList.contains("default")) {
            return "redirect:/getCategories?default";
        }

        // get all podcasts that fit the categories
        List<Podcast> podcastList = new ArrayList<>();
        for(String category : categoryList){
            long categoryId = categoryDao.getIdByName(category);
            podcastList.addAll(podcastDao.findAllByCategoryId(categoryId));
        }

        // only keep unique ones
        List<Podcast> podcastRecommendations = new ArrayList<>();
        for(Podcast p : podcastList){
            if(!podcastRecommendations.contains(p)){
                podcastRecommendations.add(p);
                List<Category> categories = new ArrayList<>(p.getCategories());
                p.setCategories(categories); // seems pointless but for some reason it is needed
            }
        }


        session.setAttribute("recommendations", podcastRecommendations);
        session.setAttribute("categoriesChosen", categoryList);
        model.addAttribute("currentUrl", request.getRequestURI());
        redirectAttributes.addFlashAttribute("message", "Here are your recommendations");
        return "redirect:/profile"; // to show the recommended podcasts in the profile page first
    }


}
