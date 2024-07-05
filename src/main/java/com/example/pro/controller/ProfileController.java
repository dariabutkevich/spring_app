package com.example.pro.controller;

import com.example.pro.model.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.pro.repository.UsersRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    @Autowired
    private UsersRepository usersRepository;

        @GetMapping("/profile")
        public String profile(Model model, HttpServletRequest request) {
            Users currentUser = (Users) request.getSession().getAttribute("currentUser");
            model.addAttribute("user", currentUser);
            return "profile";
        }
    @GetMapping("/profile/edit")
    public String showEditProfileForm(Model model, HttpServletRequest request) {
        Users currentUser = (Users) request.getSession().getAttribute("currentUser");
        model.addAttribute("user", currentUser);
        return "editProfile";
    }

    @PostMapping("/profile/edit")
    public String processEditProfileForm(@ModelAttribute("user") Users user, HttpServletRequest request) {
        Users currentUser = (Users) request.getSession().getAttribute("currentUser");

        currentUser.setUsername(user.getUsername());
        currentUser.setFirstname(user.getFirstname());
        currentUser.setLastname(user.getLastname());
        currentUser.setEmail(user.getEmail());

        usersRepository.save(currentUser);

        request.getSession().setAttribute("currentUser", currentUser);

        return "redirect:/profile";
    }
}