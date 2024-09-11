package com.example.pro.repository;

import com.example.pro.model.PhoneBook;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhoneBookRepository extends CrudRepository<PhoneBook, Long> {
    Optional<PhoneBook> findByNumber(String number);
}
