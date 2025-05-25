package com.example.newhotelsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling login page rendering.
 */
@Controller
public class LoginController {

    /**
     * Display the login page.
     *
     * @param model the model
     * @return the view name
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }
}
