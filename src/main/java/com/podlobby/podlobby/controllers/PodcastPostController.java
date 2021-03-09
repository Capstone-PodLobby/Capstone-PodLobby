package com.podlobby.podlobby.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PodcastPostController {

    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/posts/create")
        public String showPostCreate(){
            return"/posts/create";
        }


    /////////////////
    //EDIT   OPTION//
    /////////////////
    //Will need to add ID to path for specific post
    @GetMapping("/posts/edit")
    public String viewEditPostForm() {
//        model.addAttribute("post", postsDao.getOne(id));
        return "posts/edit";
    }

    /////////////////
    //DELETE OPTION//
    /////////////////
//    @PostMapping("/posts/{id}/delete")
//    public String deletePost(@PathVariable long id){
//        System.out.println("Deleting post...");
//        postsDao.deleteById(id);
//        return "redirect:/posts";
//    }

}
