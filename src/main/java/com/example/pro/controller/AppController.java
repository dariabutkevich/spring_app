package com.example.pro.controller;

import com.example.pro.model.Lot;
import com.example.pro.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AppController {

    @Autowired
    private LotRepository lotRepository;

    @GetMapping("/app")
    public String showAuctionLots(Model model) {
        List<Lot> lots = lotRepository.findAll();
        List<Lot> topLots = lots.stream().limit(2).collect(Collectors.toList());
        model.addAttribute("lots", topLots);
        return "app";
    }
}