package com.example.pro.controller;

import com.example.pro.model.Users;
import com.example.pro.model.Lot;
import com.example.pro.repository.LotRepository;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class AppPageController {
    private static final Logger logger = LoggerFactory.getLogger(AppPageController.class);

    @Autowired
    private LotRepository lotRepository;

    @GetMapping("/apppage")
    public String appPage(Principal principal, HttpSession session, Model model) {
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser != null) {
            logger.info("Rendering appPage for user");

            List<Lot> lots = lotRepository.findAll();
            model.addAttribute("lots", lots);

            return "appPage";
        } else {
            logger.info("Redirecting to /signin");
            return "redirect:/signin";
        }
    }

    @PostMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }

    @GetMapping("/apppage/lot/{id}")
    public String lotPage(@PathVariable Long id, Model model, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser != null) {
            logger.info("Rendering lotPage for lot id: {}", id);
            Lot lot = lotRepository.findById(id).orElse(null);

            if (lot != null) {
                model.addAttribute("lot", lot);
                return "lot";
            } else {
                logger.warn("Lot not found for id: {}", id);
                return "redirect:/apppage";
            }
        } else {
            logger.info("Redirecting to /signin");
            return "redirect:/signin";
        }
    }
    @GetMapping("/apppage/lot/{id}/bet")
    public String showBetPage(@PathVariable Long id, Model model, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser != null) {
            Lot lot = lotRepository.findById(id).orElse(null);
            if (lot != null) {
                model.addAttribute("lot", lot);
                return "bet";
            } else {
                return "redirect:/apppage?error=lot_not_found";
            }
        }
        return "redirect:/signin";
    }
    @PostMapping("/apppage/lot/{id}/bet")
    public String placeBet(@PathVariable Long id, @RequestParam BigDecimal bidAmount, HttpSession session) {
        Users currentUser = (Users) session.getAttribute("currentUser");
        if (currentUser != null) {
            Lot lot = lotRepository.findById(id).orElse(null);
            if (lot != null) {
                lot.setLastuser(currentUser.getUsername());
                BigDecimal minBid = lot.getCurrentprice().add(new BigDecimal("50"));
                if (bidAmount.compareTo(minBid) < 0) {
                    return "redirect:/apppage/lot/" + id + "?error=min_bid";
                }

                if (bidAmount.compareTo(lot.getCurrentprice()) > 0) {
                    lot.setCurrentprice(bidAmount);
                    lotRepository.save(lot);
                } else {
                    return "redirect:/apppage/lot/" + id + "?error=low_bid";
                }
            }
            return "redirect:/apppage/lot/" + id;
        }
        return "redirect:/signin";
    }
}