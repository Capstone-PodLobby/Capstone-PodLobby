package com.podlobby.podlobby.controllers;


import com.podlobby.podlobby.model.Podcast;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Password;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class Settings {

    private final UserService userService;
    private final UserRepository userDao;
    private final PasswordEncoder encoder;


    public Settings(UserService userService, UserRepository userDao, PasswordEncoder encoder){
        this.userService = userService;
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpServletRequest request){
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("currentUrl", request.getRequestURI());
        return "users/settings";
    }


    @PostMapping("/settings")
    public String changeInfo(HttpSession session, HttpServletRequest request, Model model, @ModelAttribute User user,
                             @RequestParam(name = "confirm-password", required = false) String confirmPassword,
                             @RequestParam(name = "profileImage", required = false) String profileImage,
                             @RequestParam(name = "backgroundImage", required = false) String backgroundImage,
                             @RequestParam(name = "description") String aboutMe,
                             RedirectAttributes redirectAtr
    ){
        User currentUser =  userService.getLoggedInUser();

        if(userDao.findByEmail(user.getEmail()) != null && !currentUser.getEmail().equals(user.getEmail())){
            return "redirect:/settings?email";
        } else {
            currentUser.setEmail(user.getEmail());
        }

        if (userDao.findByUsername(user.getUsername()) != null && !currentUser.getUsername().equals(user.getUsername())) {
            return "redirect:/settings?username";
        } else {
            currentUser.setUsername(user.getUsername());
        }
        if (!profileImage.isEmpty()) {
            currentUser.setProfileImage(profileImage);
        }

        if (!backgroundImage.isEmpty()) {
            currentUser.setBackgroundImage(backgroundImage);
        }

        if (!user.getPassword().isEmpty()) {
            if (!user.getPassword().equals(confirmPassword)) {
                return "redirect:/settings?passwords";
            }
            if(!Password.goodQualityPassword(user.getPassword())) {
                return "redirect:/settings?quality";
            }
            currentUser.setPassword(encoder.encode(user.getPassword()));
        }

        if (aboutMe.isEmpty()) {
            return "redirect:/settings?aboutMe";
        }
        currentUser.setAboutMe(aboutMe);

        userDao.save(currentUser);
        session.setAttribute("user", currentUser);
        model.addAttribute("currentUrl", request.getRequestURI());
        redirectAtr.addFlashAttribute("message", "Your information has been updated");
        return "redirect:/profile?myPodcasts";
    }
}
