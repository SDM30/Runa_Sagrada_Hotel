package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class HotelService {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private String description;
    private LocalDate sheduledDate;
    private LocalTime scheduledTime;
    private int capacity;
    private double basePrice;
    private String status;
    private List<String> imageUrls;
    private double latitude;
    private double longitude;

    public HotelService() {
    }

    public HotelService(String name, String category, String description, LocalDate sheduledDate,
            LocalTime scheduledTime, int capacity, double basePrice, String status, List<String> imageUrls,
            double latitude, double longitude) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.sheduledDate = sheduledDate;
        this.scheduledTime = scheduledTime;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.status = status;
        this.imageUrls = imageUrls;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
