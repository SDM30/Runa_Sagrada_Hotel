package com.runasagrada.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class ServiceOffering {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private String subcategory;
    private String description;

    @OneToMany(mappedBy = "service")
    @OrderBy("schedDate ASC, startTime ASC")
    private List<ServiceSchedule> serviceSchedules;

    private double basePrice;
    private int durationMinutes;
    private List<String> imageUrls;
    private int maxParticipants;
    private double latitude;
    private double longitude;

    public ServiceOffering() {
    }

    public ServiceOffering(String name, String category, String subcategory, String description,
            List<ServiceSchedule> serviceSchedules, double basePrice, int durationMinutes, List<String> imageUrls,
            int maxParticipants, double latitude, double longitude) {
        this.name = name;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
        this.serviceSchedules = serviceSchedules;
        this.basePrice = basePrice;
        this.durationMinutes = durationMinutes;
        this.imageUrls = imageUrls;
        this.maxParticipants = maxParticipants;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
