package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PodcastPostController {


    @GetMapping("/posts/create")
        public String showPostCreate(){
            return"/posts/create";
        }

    //            Will need to add ID to path for specific post
    @GetMapping("/posts/edit")
    public String viewEditPostForm() {
//        model.addAttribute("post", postsDao.getOne(id));
        return "posts/edit";
    }

}
