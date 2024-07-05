package com.example.pro.controller;

import com.example.pro.model.Users;
import com.example.pro.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UsersRepository usersRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String admin(Model model, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser != null && currentUser.getRole().equals("admin")) {
            logger.info("Rendering admin page");
            return "admin";
        } else {
            logger.info("Redirecting to /signin");
            return "redirect:/signin";
        }
    }

    @PostMapping("/admin/users")
    public String saveUsers(@ModelAttribute("newUser") Users newUser, Model model) {
        Optional<Object> existingUser = usersRepository.findByEmail(newUser.getEmail());
        if (existingUser.isPresent()) {
            model.addAttribute("message", "The data is already present in the users.");
            model.addAttribute("users", getNonAdminUsers());
            return "users";
        }

        usersRepository.save(newUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", getNonAdminUsers());
        return "users";
    }

    private List<Users> getNonAdminUsers() {
        List<Users> allUsers = (List<Users>) usersRepository.findAll();
        return allUsers.stream()
                .filter(u -> !u.getRole().equals("admin"))
                .collect(Collectors.toList());
    }
    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long userId) {
        Users user = usersRepository.findById(userId).orElse(null);
        if (user != null && !user.getRole().equals("admin")) {
            usersRepository.delete(user);
        }
        return "redirect:/admin/users";
    }
}