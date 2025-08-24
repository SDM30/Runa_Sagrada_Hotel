package com.runasagrada.demo.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    private HotelUser user;

    // @OneToMany(mappedBy = "client")
    // private List<Reservation> reservations = new ArrayList<>();
    public Client(HotelUser user) {
        this.user = user;
    }

    public Client() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HotelUser getUser() {
        return user;
    }

    public void setUser(HotelUser user) {
        this.user = user;
    }

}