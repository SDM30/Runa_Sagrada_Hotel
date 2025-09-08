package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@Table(name = "Hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    private Long id;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "latitude", length = 255)
    private String latitude;

    @Column(name = "longitude", length = 255)
    private String longitude;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToMany
    @JoinTable(name = "hotel_amenities", joinColumns = @JoinColumn(name = "hotel_id"), inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<Amenity> amenities;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @JsonManagedReference("hotel-rooms") // avoid serialization recursion (rooms dont load)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<ServiceOffering> services;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<Department> departments;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private List<StaffMember> staffMembers;

    public Hotel() {
    }
}
