package com.example.pro.controller;

import com.example.pro.model.PhoneBook;
import com.example.pro.repository.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PhoneBookController {

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    @GetMapping("/phonebook-input")
    public String phoneBook(Model model) {
        model.addAttribute("newBook", new PhoneBook());
        return "phoneBookInput";
    }

    @PostMapping("/phonebook-table")
    public String savePhoneBookEntry(@ModelAttribute("newBook") PhoneBook newBook, Model model) {
        Optional<PhoneBook> existingBook = phoneBookRepository.findByNumber(newBook.getNumber());
        if (existingBook.isPresent()) {
            model.addAttribute("message", "The data is already present in the phone book.");

            model.addAttribute("books", phoneBookRepository.findAll());
            return "phoneBookTable";
        }

        phoneBookRepository.save(newBook);
        return "redirect:/phonebook-table";
    }

    @GetMapping("/phonebook-table")
    public String phoneBookTable(Model model) {
        model.addAttribute("books", phoneBookRepository.findAll());
        return "phoneBookTable";
    }
}
