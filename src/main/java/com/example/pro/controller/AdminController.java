package com.example.pro.controller;

import com.example.pro.model.Users;
import com.example.pro.repository.UsersRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.pro.repository.LotRepository;
import com.example.pro.model.Lot;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

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
        Optional<Object> existingUser = Optional.ofNullable(usersRepository.findByEmail(newUser.getEmail()));
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

    @Autowired
    private LotRepository lotRepository;

    @GetMapping("/admin/lots/createlot")
    public String showCreateLotForm(Model model) {
        model.addAttribute("lot", new Lot());
        return "createlot";
    }

    @PostMapping("/admin/lots")
    public String createLot(@ModelAttribute Lot lot, @RequestParam("photoUrl") String photoUrl, Model model) {
        // Проверка наличия URL фото
        if (photoUrl == null || photoUrl.isEmpty()) {
            model.addAttribute("errorMessage", "URL фото обязателен.");
            return "createlot";
        }
        lot.setPhoto(photoUrl); // Установка URL фото

        // Проверка других полей
        if (lot.getLotname() == null || lot.getDescription() == null || lot.getStartprice() == null) {
            model.addAttribute("errorMessage", "Все поля должны быть заполнены.");
            return "createlot";
        }
        lot.setCurrentprice(lot.getStartprice());
        // Сохранение лота
        try {
            lotRepository.save(lot);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка сохранения лота: " + e.getMessage());
            return "createlot";
        }

        return "redirect:/admin/lots";
    }
    @GetMapping("/admin/lots")
    public String viewAllLots(Model model) {
        model.addAttribute("lots", lotRepository.findAll());
        return "lots";
    }
    @PostMapping("/admin/lots/delete/{id}")
    public String deleteLot(@PathVariable("id") Long lotId) {
        lotRepository.deleteById(lotId);
        return "redirect:/admin/lots";
    }
    @GetMapping("/admin/lots/editlot/{id}")
    public String showEditLotForm(@PathVariable("id") Long lotId, Model model) {
        Lot lot = lotRepository.findById(lotId).orElseThrow(() -> new IllegalArgumentException("Invalid lot Id:" + lotId));
        model.addAttribute("lot", lot);
        return "editlot";
    }
    @PostMapping("/admin/lots/update/{id}")
    public String updateLot(@PathVariable("id") Long lotId, @ModelAttribute Lot lot, @RequestParam("photoUrl") String photoUrl) {
        Lot existingLot = lotRepository.findById(lotId).orElseThrow(() -> new IllegalArgumentException("Invalid lot Id:" + lotId));

        existingLot.setLotname(lot.getLotname());
        existingLot.setDescription(lot.getDescription());
        existingLot.setStartprice(lot.getStartprice());
        existingLot.setPhoto(photoUrl);

        lotRepository.save(existingLot);
        return "redirect:/admin/lots";
    }
}