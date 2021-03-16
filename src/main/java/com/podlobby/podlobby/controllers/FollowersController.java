package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.FollowRepository;
import com.podlobby.podlobby.repositories.PodcastRepository;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FollowersController {

    private final UserService userService;
    private final FollowRepository followDao;
    private final UserRepository userDao;
    private final PodcastRepository podcastDao;


    public FollowersController(FollowRepository followDao, UserRepository userDao, PodcastRepository podcastDao, UserService userService){
        this.followDao = followDao;
        this.userDao = userDao;
        this.podcastDao = podcastDao;
        this.userService = userService;
    }


    // seeing all of the users you follow
    @GetMapping("/following")
    public String showFollowers(Model model, HttpServletRequest request){
        User user = userService.getLoggedInUser();
        model.addAttribute("currentUrl", request.getRequestURI());
        model.addAttribute("followingList", followDao.findAllByUserId(user.getId()));
        model.addAttribute("user", user);
        return "users/following";
    }


    // viewing the page of the user you follow
    @GetMapping("/following/{id}")
    public String viewFollowersProfile(Model model, @PathVariable(name = "id") long id, HttpServletRequest request){
        User following = userDao.findById(id).get();
        model.addAttribute("following", following);

        User currUser = userService.getLoggedInUser();
        model.addAttribute("user", currUser);

        model.addAttribute("currentUrl", request.getRequestURI());

        List<Podcast> createdPodcasts = podcastDao.findAllByUserId(id);
        model.addAttribute("followingPodcasts", createdPodcasts);
        model.addAttribute("userController", userDao);
        model.addAttribute("isFollowing", true); // viewing a followers page -> you are following them
        return "users/othersProfile";
    }


    // unfollow a user by id
    @GetMapping("/unfollow/{id}")
    public String unfollowUser(Model model, HttpServletRequest request, HttpSession session, @PathVariable(name = "id") long id){

        User unfollow = userDao.getOne(id);

        User currUser = (User) session.getAttribute("user");

        List<User> currentFollowList = followDao.findAllByUserId(currUser.getId());// get all followers for currently logged in user

        currentFollowList.remove(unfollow);

        currUser.setUsers(currentFollowList);
        followDao.save(currUser);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/following?unfollowed=" + unfollow.getUsername();
    }

    // following the user whose page you are on
    @GetMapping("/followUser/{id}")
    public String followAUser(Model model, HttpServletRequest request, HttpSession session, @PathVariable(name = "id") long id){
        // add this user to the current user's follow list
        User user = (User) session.getAttribute("user");
        User userToFollow = userDao.getOne(id);

        List<User> currentFollowList = followDao.findAllByUserId(user.getId());// get all followers for currently logged in user

        currentFollowList.add(userToFollow);

        user.setUsers(currentFollowList);
        followDao.save(user);
        model.addAttribute("userController", userDao);
        model.addAttribute("isFollowing", true); // viewing a followers page that you just followed
        model.addAttribute("currentUrl", request.getRequestURI());
        return "redirect:/otherProfile/" + id + "?followed";
    }


//    view all the users who follow you
    @GetMapping("/followers")
    public String viewMyFollowers(Model model){

        User user = userService.getLoggedInUser();// get the current user

        List<User> userFollowersList = followDao.findAllFollowersById(user.getId());// get a list of user's current followers

        model.addAttribute("listOfFollowers", userFollowersList);
        return "users/followers";
    }

}
