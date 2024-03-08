package com.shop.prshop.controller;

import com.shop.prshop.model.user.User;
import com.shop.prshop.repository.UserRepository;
import com.shop.prshop.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    public UserServiceImpl userService;

    @Autowired
    public SecurityController(UserServiceImpl userService) {
        this.userService = userService;
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
    public String saveUser(User user) {
        userService.saveUser(user);
        return "redirect:/showMyLoginPage";
    }
}
