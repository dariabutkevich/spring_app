package com.example.pro.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
@SpringBootTest
@AutoConfigureMockMvc
class CustomErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleNonExistentPath() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/non-existent-path"))
                .andExpect(status().isNotFound());
    }


}