package com.example.pro.controller;

import com.example.pro.model.Task7;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@org.springframework.stereotype.Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @PostMapping("/multiply")
    public String handleMultiply(@RequestParam("number") int number, Model model){
        int result = number * 2;
        model.addAttribute("result", result);
        model.addAttribute("number", number);
        return "multiplication";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        return "500";
    }

    @PostMapping("/phonebook-input")
    public String phoneBook(Model model) {
        return "phoneBookInput";
    }

    @GetMapping("/main-task7")
    public String getTask7(Model model) {
        model.addAttribute("formData", new Task7());
        return "task7";
    }
    @PostMapping("/app")
    public String app(Model model) {
        return "app";
    }

}