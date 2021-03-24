package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Category;
import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.CategoryRepository;
import com.podlobby.podlobby.repositories.CommentRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.IframeParser;
import com.podlobby.podlobby.util.Methods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;


@Controller
public class PodcastPostController {

    private final CategoryRepository categoryDao;
    private final PodcastRepository podcastDao;
    private final UserRepository userDao;
    private final CommentRepository commentDao;
    private final UserService userService;
    private final TLSEmail tlsEmail;

    public PodcastPostController(CategoryRepository categoryDao, CommentRepository commentDao, UserService userService, PodcastRepository podcastDao, UserRepository userDao, TLSEmail tlsEmail){
        this.categoryDao = categoryDao;
        this.podcastDao = podcastDao;
        this.userDao = userDao;
        this.userService = userService;
        this.commentDao = commentDao;
        this.tlsEmail = tlsEmail;
    }

    /////////////////
    //EDIT   OPTION//
    /////////////////

    @GetMapping("/podcasts/{id}")
    public String podcastsView(Model model, @PathVariable long id) {
        Podcast podcast = podcastDao.getOne(id);
        model.addAttribute("podcast", podcast);
        return "podcasts/show";
    }


    @GetMapping("/podcasts/{id}/edit")
    public String viewEditPodcastForm(@PathVariable long id, Model model){
        long currUserName = userService.getLoggedInUser().getId();

        int currUserNameIsAdmin = userService.getLoggedInUser().getIsAdmin();

        Podcast tryingToEdit = podcastDao.getOne(id);
        long findingInfo = tryingToEdit.getUser().getId();
        model.addAttribute("podcast",podcastDao.getOne(id));

        if (currUserName == findingInfo || currUserNameIsAdmin == 1) {
            return "podcasts/edit";
        }
        return "redirect:/profile?edit";
    }


    @PostMapping("/podcasts/{id}/edit")
    public String editPodcast(@PathVariable long id, @ModelAttribute Podcast podcast) {

        podcast.setCreatedAt(new Timestamp(new Date().getTime()));
        podcast.setUser(userDao.getOne(userService.getLoggedInUser().getId()));
        podcastDao.save(podcast);
        return "redirect:/profile?edited";
    }


    @GetMapping("/podcast/delete/{id}")
    public String deletePodcast(Model model, @PathVariable(name = "id") long id, HttpServletRequest request, @RequestParam(name = "currentUrl") String currentUrl){
        // cascade all is in the podcast model but podcast delete errors out. comments and podcast category id needs to be removed first
        podcastDao.delete(podcastDao.getOne(id));
        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/profile?deleted";
    }


    /////////////////
    //    DISPLAY  //
    /////////////////
    @GetMapping("/podcasts/create")
    public String showPodcastCreate(Model model, HttpServletRequest request, HttpSession session){
        User user = userService.getLoggedInUser();
        session.setAttribute("user", user);
        model.addAttribute("podcast", new Podcast());
        model.addAttribute("categoryList", categoryDao.findAll());
        model.addAttribute("currentUrl", request.getRequestURI());
        return"podcasts/create";
    }



    @PostMapping("/podcasts/create")
    public String createPodcast(Model model, @ModelAttribute @Validated Podcast podcast, Errors validation, @RequestParam(name = "categories", required = false) String categories, HttpServletRequest request) throws ServletException, IOException {

        List<String> errorMsg = new ArrayList<>();

        if (podcast.getTitle().isEmpty()) {
            validation.rejectValue("title", "Title can not be missing");
            errorMsg.add("Title can not be missing");
        }
        if (podcast.getDescription().isEmpty()) {
            validation.rejectValue("description", "Description can not be missing");
            errorMsg.add("Description can not be missing");
        }
        if (podcast.getEmbedLink().isEmpty()) {
            validation.rejectValue("embedLink", "Embed Link can not be missing, see help");
            errorMsg.add("Embed link can not be missing, see help");
        }
        if (podcast.getCategories().isEmpty()) {
            validation.rejectValue("categories", "Please include at least one category");
            errorMsg.add("Please include at least one category");
        }
        if (IframeParser.parseIframe(podcast.getEmbedLink()).equalsIgnoreCase("nosrc")){
            validation.rejectValue("embedLink", "Issue with embed link, please check src= attribute");
            errorMsg.add("Issue with embed link, please check src= attribute");
        }
        if(podcastDao.getByTitle(podcast.getTitle()) != null) {
            validation.rejectValue("title", "Please pick a unique podcast Title");
            errorMsg.add("Title can not be the same as another Podcast");
        }

        if(validation.hasErrors()) {
            model.addAttribute("podcast", podcast);
            model.addAttribute("errorList", errorMsg);
            model.addAttribute("categoryList", categoryDao.findAll());
            model.addAttribute("currentUrl", request.getRequestURI());
            return"podcasts/create";
        }

        if(podcast.getImage().isEmpty()){ // set the default image if one is not provided
            podcast.setImage("https://q3p9g6n2.rocketcdn.me/wp-content/ml-loads/2017/02/microphone-podcast-radio-ss-1920.jpg");
        }

        // give the categories to the podcast
        List<Category> categoryList = new ArrayList<>();
        String[] catList = categories.split(",");

        for(String c : catList) {
            Long catId = Long.parseLong(c) + 1;
            categoryList.add(categoryDao.getOne(catId));
        }

        podcast.setCategories(categoryList);

        User currUser = userService.getLoggedInUser();

        podcast.setCreatedAt(new Timestamp(new Date().getTime()));
        podcast.setUser(userDao.getOne(currUser.getId())); // ----- GET LOGGED IN USER -> session ?
        podcast.setEmbedLink(IframeParser.parseIframe(podcast.getEmbedLink())); // parse it before it is stored in the database
        podcastDao.save(podcast);

        List<Podcast> userList = podcastDao.findAllByUserId(currUser.getId());
        String message = "Thank you " + currUser.getUsername() + " for adding your " + Methods.numberSuffix(userList.size()) + " podcast, it can be found on your profile!";
        tlsEmail.sendEmail(currUser.getEmail(), currUser.getUsername(), "Your podcasts has been added", message);

        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/profile?myPodcasts";
    }


}
