package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.repositories.CommentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Comment {

    private final CommentRepository commentDao;

    public Comment(CommentRepository commentDao){
        this.commentDao = commentDao;
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(Model model, @PathVariable(name = "id") long id){
        commentDao.delete(commentDao.getOne(id));
        return "redirect:/profile?comment";
    }

    @PostMapping("/comment/create")
    public String createComment(@RequestParam (name = "comment") String commentContent){

        System.out.println(commentContent);
        return "redirect:/profile"; // would like to grab the current page you are on
    }

}
