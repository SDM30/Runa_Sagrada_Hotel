package com.runasagrada.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private HotelUser user;
    // @OneToMany(mappedBy = "client")
    // private List<Reservation> reservations = new ArrayList<>();
}