package com.runasagrada.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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


    public HotelUser(Long id, String name, String email, String password, String phone, String nationalId, String profileIcon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nationalId = nationalId;
        this.profileIcon = profileIcon;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(String profileIcon) {
        this.profileIcon = profileIcon;
    }


}