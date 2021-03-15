package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Comment;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.repositories.CommentRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.Timestamp;

@Controller
public class CommentController {

    private final CommentRepository commentDao;
    private final UserService userService;
    private final PodcastRepository podcastDao;

    public CommentController(CommentRepository commentDao, PodcastRepository podcastDao, UserService userService){
        this.commentDao = commentDao;
        this.userService = userService;
        this.podcastDao = podcastDao;
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(Model model, @PathVariable(name = "id") long id, @RequestParam(name = "currentUrl") String currentUrl){
        commentDao.delete(commentDao.getOne(id));
        return "redirect:" + currentUrl +"?comment";
    }

    @PostMapping("/comment/create/{title}")
    public String createComment(@RequestParam (name = "comment") String commentContent, @PathVariable(name = "title") String title, HttpServletRequest request, @RequestParam(name = "currentUrl") String currentUrl){

        Podcast p = podcastDao.getByTitle(title);
        Comment comment = new Comment();
        comment.setComment(commentContent);
        comment.setCreatedAt(new Timestamp(new Date().getTime()));
        comment.setUser(userService.getLoggedInUser());
        comment.setPodcast(p);
        commentDao.save(comment);
        return "redirect:" + currentUrl + "?commentAdded";
    }

}
