package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    private final UserService userService;
    private final UserRepository userDao;
    private final TLSEmail tlsEmail;
    private final PasswordEncoder encoder;

    public LoginController(UserService userService, TLSEmail tlsEmail, UserRepository userDao, PasswordEncoder encoder){
        this.userService = userService;
        this.tlsEmail = tlsEmail;
        this.userDao = userDao;
        this.encoder = encoder;
    }


    //    Display login page   //
    @GetMapping("/login")
    public String showLoginForm(Model model, HttpServletRequest request){

        // if a user is currently logged in
        User user = null;

        try {
            user = userService.getLoggedInUser();
        } catch (Exception ignored){}

        if(user != null){
            model.addAttribute("currentUrl", request.getRequestURI());
            return "redirect:/profile?myPodcasts";
        }
        return "users/login";
    }

    // forgot password is clicked
    @GetMapping("/forgotPassword")
    public String forgotPassword(){
        return "authentication/forgotPasswordForm";
    }

    // user name and email has been added to form and sent
    // email out the password and set the current user for that info their password to the get of ran password
    @PostMapping("/forgotPassword")
    public String forgotPasswordSent(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email){

        User user = null;

        try {
            user = userDao.findByUsername(username);
        } catch (Exception ignored){}

        if(user == null){
            return "redirect:/forgotPassword?username";
        }

        if( !user.getEmail().equals(email) ) {
            return "redirect:/forgotPassword?email";
        }

        tlsEmail.sendEmail(email, username, "", "", true);
        String newPassword = Password.getThePassword().get(0);
        user.setPassword(encoder.encode(newPassword)); // set the password to the one that is randomly generated;
        userDao.save(user);
        return "authentication/forgotPasswordSent";
    }

    // link from email sends them here to then "login" with that temp password
    @GetMapping("/reset")
    public String loginInTempPassword(){
        return "authentication/tempPassword";
    }


    // compare what was entered with what was set for that user and if good send them to then create a new password
    @PostMapping("/reset")
    public String tempPasswordVerified(HttpSession session, @RequestParam(name = "username") String username, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password){
        User user = null;

        try {
            user = userDao.findByUsername(username);
        } catch (Exception ignored){}

        if(user == null){
            return "redirect:/reset?username";
        }

        if( !user.getEmail().equals(email) ) {
            return "redirect:/reset?email";
        }

        if( !encoder.matches(password, user.getPassword()) ) {
            return "redirect:/reset?incorrect";
        }

        session.setAttribute("user", user);
        return "authentication/newPassword";
    }

    @GetMapping("/newPassword")
    public String newPasswordForm(){
        return "authentication/newPassword";
    }

    // check quality of new password and that they match before setting it to that user
    //send them back to log in now that the password has been changed
    @PostMapping("/newPassword")
    public String newPasswordMade(HttpSession session, @RequestParam(name = "password") String password,
                                  @RequestParam(name = "confirmPassword") String confirm, RedirectAttributes redirectAtr){
        if ( !password.equals(confirm) ){
            redirectAtr.addFlashAttribute("message", "Passwords do not match");
            return "redirect:/newPassword";
        } else if (!Password.goodQualityPassword(password)) {
            redirectAtr.addFlashAttribute("message", "Password must be 8-20 characters, contain 1 Uppercase, and 1 number");
            return "redirect:/newPassword";
        }
        User user = (User) session.getAttribute("user");
        user.setPassword(encoder.encode(password));
        userDao.save(user);
        return "redirect:/login";
    }


}
