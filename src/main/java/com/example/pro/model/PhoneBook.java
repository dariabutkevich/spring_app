package com.example.pro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "phone_book")
@EntityListeners(AuditingEntityListener.class)
public class PhoneBook {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "name", nullable = false)
    private String name;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}