package com.example.newhotelsystem.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the home page and general navigation.
 */
@Controller
public class HomeController {

    /**
     * Display the home page.
     *
     * @param session the HTTP session
     * @param model the model
     * @return the view name
     */
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // Get username from session if available
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username);
        }
        return "home";
    }
}
