package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.sql.Timestamp;


@Controller
public class RegisterController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final TLSEmail tlsEmail;


    public RegisterController(UserRepository userDao, PasswordEncoder encoder, UserService userService, TLSEmail tlsEmail) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.userService = userService;
        this.tlsEmail = tlsEmail;
    }

    //    Display registration page   //
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpServletRequest request){

        // if a user is currently logged in
        User user = null;
        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            model.addAttribute("currentUrl", request.getRequestURI());
            return "redirect:/profile?myPodcasts";
        }

        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String registered(Model model, @ModelAttribute User user, @RequestParam(name = "confirm-password", required = false) String confirmPassword,
                             @RequestParam(name = "g-recaptcha-response") String captcha) {
        if(userDao.findByUsername(user.getUsername()) != null) {
            model.addAttribute("username", user.getUsername());
            return "redirect:/register?username";
        } else if(userDao.findByEmail(user.getEmail()) != null) {
            model.addAttribute("email", user.getEmail());
            return "redirect:/register?email";
        } else if(user.getAboutMe().isEmpty()) {
            model.addAttribute("about", user.getAboutMe());
            return "redirect:/register?about";
        } else if(!confirmPassword.equals(user.getPassword())) {
            model.addAttribute("mismatch", 0);
            return "redirect:/register?password";
        } else if(captcha.isEmpty()){
            return "redirect:/register?captcha";
        } else if (!Password.goodQualityPassword(user.getPassword())){
            return "redirect:/register?quality";
        }

        user.setIsAuthenticated(0); // they need to activate their account // NEEDS PRODUCTION TESTING
        user.setIsAdmin(0);
        String accountAuthCode = Password.randomRegisterCode();
        user.setAuthCode(accountAuthCode);
//        user.setIsAuthenticated(1); // for production for now

        user.setJoinedAt(new Timestamp(new Date().getTime()));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setBackgroundImage("https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1");
        user.setProfileImage("https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8bWljcm9waG9uZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80");

        userDao.save(user);

        // testing
        String emailContent = "Thank you " + user.getUsername() + " for signing up at PodLobby!. Please follow this link to activate your account. http://localhost:8080/activate/" + user.getId() + "/" + user.getAuthCode();
        // production
//        String emailContent = "Thank you " + user.getUsername() + " for signing up at PodLobby!. Please follow this link to activate your account. https://podlobby.club/activate/" + user.getId() + "/" + user.getAuthCode();
        tlsEmail.sendEmail(user.getEmail(), user.getUsername(), "Welcome to PodLobby", emailContent);
        return "redirect:/newAccount";
//        return "redirect:/getCategories";
    }

    // page telling user to check their email
    @GetMapping("/newAccount")
    public String accountNeedsActivation(){
        return "authentication/afterRegister";
    }


    // link in email that will activate their account
    @GetMapping("/activate/{id}/{code}")
    public String activateAccount(@PathVariable(name = "code") String code, @PathVariable(name = "id") long id){
        User user = userDao.getOne(id);
        if(code.equals(user.getAuthCode())) { // ensure they actually clicked the email link rather than typing something into the browser
            user.setIsAuthenticated(1);
            userDao.save(user);
            return "redirect:/login?activated";
        }
        return "redirect:/login?activationIssue";
    }



    // view the page to change your password
    @GetMapping("/admin/passwordChange")
    public String adminGranted(){
        return "admin/granted";
    }

    @PostMapping("/admin/passwordChange")
    public String changePassword(@RequestParam(name = "password") String password, @RequestParam(name = "confirm") String confirmPassword, RedirectAttributes redirectAttributes){
        User user = userService.getLoggedInUser();
        if(!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("message", "Passwords do not match");
            return "redirect:/admin/passwordChange";
        } else if (!Password.goodQualityPassword(password)) {
            redirectAttributes.addFlashAttribute("message", "Password must be 8-20 characters, contain 1 Uppercase, and 1 number");
            return "redirect:/admin/passwordChange";
        }
        user.setPassword(encoder.encode(password));
        user.setIsAdmin(1);
        userDao.save(user);
        redirectAttributes.addFlashAttribute("message", "You are now an Admin and your password has been updated");
        return "redirect:/profile";
    }

}
