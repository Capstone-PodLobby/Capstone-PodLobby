package com.podlobby.podlobby.controllers;

import com.podlobby.podlobby.RecaptchaResponse;
import com.podlobby.podlobby.model.User;
import com.podlobby.podlobby.repositories.UserRepository;
import com.podlobby.podlobby.services.TLSEmail;
import com.podlobby.podlobby.services.UserService;
import com.podlobby.podlobby.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.sql.Timestamp;
import java.util.List;


@Controller
public class RegisterController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final TLSEmail tlsEmail;


    @Value("${recaptcha.checkbox.secret.key}")
    private String recaptchaCheckboxSecret;

    @Value("${recaptcha.testing.secret.key}")
    private String recaptchaTestingSecret;

    private final String recaptchaServerURL = "https://www.google.com/recaptcha/api/siteverify";


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private ReCaptchaValidationService reCaptchaValidationService;


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
        model.addAttribute("registerMessage", "Create an Account");
        return "users/register";
    }

    @PostMapping("/register")
    public String registered(Model model, @ModelAttribute @Validated User user, Errors validation, @RequestParam(name = "confirm-password", required = false) String confirmPassword,
                             @RequestParam(name = "g-recaptcha-response") String captcha, RedirectAttributes redirectAttributes) {
        List<String> errorMsg = new ArrayList<>();

        if(!verifyCaptcha(captcha)) {
            errorMsg.add("Please verify you are not a robot");
        }

        if(userDao.findByUsername(user.getUsername()) != null) {
            validation.rejectValue("username", "Username can not be the same as another user");
            errorMsg.add("Username can not be the same as another user");
        }
        if(userDao.findByEmail(user.getEmail()) != null) {
            validation.rejectValue("email", "Email is already in use for another account");
            errorMsg.add("Email is already in use for another account");
        }
        if(user.getAboutMe().isEmpty()) {
            validation.rejectValue("aboutMe", "About me can not be blank");
            errorMsg.add("About me can not be blank");
        }
        if(!confirmPassword.equals(user.getPassword())) {
            errorMsg.add("Passwords do not match");
        }
        if (!Password.goodQualityPassword(user.getPassword())){
            validation.rejectValue("password", "Password must be 8-20 characters, contain 1 Uppercase, and 1 number.");
            errorMsg.add("Password must be 8-20 characters, contain 1 Uppercase, and 1 number.");
        }
        if (user.getUsername().isEmpty()){
            validation.rejectValue("username", "Username can not be blank.");
            errorMsg.add("Username can not be blank.");
        }

        if(validation.hasErrors()){
            model.addAttribute("errorList", errorMsg);
            model.addAttribute("registerMessage", "Error Creating an Account");
            model.addAttribute("user", user);
            return "users/register";
        }

        user.setIsAuthenticated(0); // they need to activate their account
        user.setIsAdmin(0);
        String accountAuthCode = Password.randomRegisterCode();
        user.setAuthCode(accountAuthCode);

        user.setJoinedAt(new Timestamp(new Date().getTime()));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setBackgroundImage("https://wallpaperaccess.com/full/4061951.jpg");
        user.setProfileImage("/images/ProfilePic.png");

        userDao.save(user);

        // testing
//        String emailContent = "Thank you " + user.getUsername() + " for signing up at PodLobby!. Please follow this link to activate your account. http://localhost:8080/activate/" + user.getId() + "/" + user.getAuthCode();
        // production
        String emailContent = "Thank you " + user.getUsername() + " for signing up at PodLobby!. Please follow this link to activate your account. https://podlobby.club/activate/" + user.getId() + "/" + user.getAuthCode();
        tlsEmail.sendEmail(user.getEmail(), user.getUsername(), "Welcome to PodLobby", emailContent);
        return "redirect:/newAccount";
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

    private boolean verifyCaptcha(String captchaResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaCheckboxSecret);
//        map.add("secret", recaptchaTestingSecret);
        map.add("response", captchaResponse);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RecaptchaResponse response = restTemplate.postForObject(recaptchaServerURL, request, RecaptchaResponse.class);
        if(response == null) {
            return false;
        }
        return response.isSuccess();
    }

}
