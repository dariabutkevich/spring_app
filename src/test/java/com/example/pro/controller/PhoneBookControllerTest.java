package com.example.pro.controller;

import com.example.pro.model.PhoneBook;
import com.example.pro.repository.PhoneBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PhoneBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneBookRepository phoneBookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(phoneBookRepository.findByNumber("1234567890")).thenReturn(Optional.empty());
    }

    @Test
    void testSavePhoneBookEntry_DuplicateNumber() throws Exception {
        PhoneBook existingBook = new PhoneBook();
        existingBook.setNumber("1234567890");
        lenient().when(phoneBookRepository.findByNumber(eq("1234567890"))).thenReturn(Optional.of(existingBook));

        PhoneBook newBook = new PhoneBook();
        newBook.setNumber("1234567890");

        mockMvc.perform(MockMvcRequestBuilders.post("/phonebook-table")
                        .flashAttr("newBook", newBook))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("message", "The data is already present in the phone book."))
                .andExpect(MockMvcResultMatchers.view().name("phoneBookTable"));

        verify(phoneBookRepository, Mockito.times(1)).findByNumber(eq("1234567890"));
        verify(phoneBookRepository, Mockito.never()).save(Mockito.any(PhoneBook.class));
    }
}