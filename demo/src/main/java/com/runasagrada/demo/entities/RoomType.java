package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@Table(name = "RoomType") // matches your INSERTs
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "base_price", precision = 12, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Column(name = "max_occupancy", nullable = false)
    private Integer maxOccupancy;

    @Column(length = 1000)
    private String amenities; // comma-separated list per your mock data

    public RoomType() {
    }
}
