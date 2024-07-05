package com.example.pro.controller;

import com.example.pro.model.Task7;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Controller
public class Task7Controller {

    private static final String TASK_7_JSON_FILE = "src/main/resources/static1/task7.json";
    private final List<Task7> dataList = new ArrayList<>();

    @GetMapping("/task7")
    public String showForm(Model model) {
        model.addAttribute("formData", new Task7());
        return "task7";
    }

    @PostMapping("/task7")
    public String saveForm(@ModelAttribute("formData") Task7 formData, Model model) {
        dataList.add(formData);

        try {
            saveDataToFile(dataList);
            model.addAttribute("success", true);
        } catch (IOException e) {
            model.addAttribute("success", false);
        }
        model.addAttribute("formData", new Task7());
        return "task7";
    }

    private void saveDataToFile(List<Task7> dataList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(TASK_7_JSON_FILE), dataList);
    }
}