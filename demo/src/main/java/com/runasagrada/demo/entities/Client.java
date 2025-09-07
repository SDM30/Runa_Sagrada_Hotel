package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    private HotelUser user;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<ServiceRating> serviceRatings = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public Client(HotelUser user) {
        this.user = user;
    }

    public Client() {
    }
}
