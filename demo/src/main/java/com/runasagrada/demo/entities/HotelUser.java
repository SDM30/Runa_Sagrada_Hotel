package com.runasagrada.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class HotelUser {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String nationalId;
    private String profileIcon;

    public HotelUser(String name, String email, String password, String phone, String nationalId, String profileIcon) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nationalId = nationalId;
        this.profileIcon = profileIcon;
    }

    public HotelUser() {
    }
}