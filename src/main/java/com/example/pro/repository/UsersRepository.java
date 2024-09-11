package com.example.pro.repository;

import com.example.pro.model.PhoneBook;
import com.example.pro.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Users findByUsername(String username);

    Users save(Users user);
;
}
