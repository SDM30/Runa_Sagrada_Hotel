package com.runasagrada.demo.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private HotelUser user;

    public Client(HotelUser user) {
        this.user = user;
    }

    public Client() {
    }
}