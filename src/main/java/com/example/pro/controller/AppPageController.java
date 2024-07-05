package com.example.pro.controller;

import com.example.pro.model.Users;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
public class AppPageController {
    private static final Logger logger = LoggerFactory.getLogger(AppPageController.class);
    @GetMapping("/apppage")
    public String appPage(Principal principal, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser != null) {
            logger.info("Rendering appPage for user");
            return "appPage";
        } else {
            logger.info("Redirecting to /signin");
            return "redirect:/signin";
        }
    }
//@PostMapping("/apppage")
//public String apppage(Model model) {
//    return "apppage";
//}
    @PostMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }
}