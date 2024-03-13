package com.shop.prshop.controller;

import com.shop.prshop.model.user.User;
import com.shop.prshop.repository.UserRepository;
import com.shop.prshop.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class SecurityController {

    public UserServiceImpl userService;
    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);


    @Autowired
    public SecurityController(UserServiceImpl userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "loginpage";
    }

    @GetMapping("/showRegistrationPage")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registerform";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user, Model model, HttpServletRequest request) {
        String email = user.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            model.addAttribute("errorEmail", true);
            return "registerform";
        }
        else {
            userService.saveUser(user, request.getRequestURL().toString().replace(request.getServletPath(), ""));
        }

        return "registrationSuccess";
    }

    @GetMapping("/showChangePasswordPage")
    public String showChangePasswordPage() {
        return "changePasswordForm";
    }

    @PostMapping("/saveChangedPassword")
    public String saveChangedPassword(Principal principal, Model model,
                                      @RequestParam("oldPassword") String oldPassword,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("repNewPassword") String repNewPassword) {
        String email = principal.getName();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.get();

        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("errorOld", true);
        }
        if(!newPassword.equals(repNewPassword)) {
            model.addAttribute("errorNew", true);
        }

        if (passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(repNewPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            model.addAttribute("succChange", true);
        }

        return "changePasswordForm";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
//        User user = userRepository.findByVerificationCode(code);
//
//        if (user == null || user.isEnabled()) {
//            return "home";
//        } else {
//            user.setVerificationCode(null);
//            user.setEnabled(true);
//            userRepository.save(user);
//
//            return "applepage";
//        }
        return null;
    }
}
