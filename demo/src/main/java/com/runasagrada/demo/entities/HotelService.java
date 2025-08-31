package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class HotelService {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String category;
    private String description;

    @OneToMany(mappedBy = "service")
    @OrderBy("sched_date ASC, sched_time ASC")
    private List<ServiceSchedule> serviceSchedules;

    private double basePrice;
    private String status;
    private List<String> imageUrls;
    private double latitude;
    private double longitude;

    public HotelService() {
    }

    public HotelService(String name, String category, String description, List<ServiceSchedule> serviceSchedules,
            double basePrice, String status, List<String> imageUrls, double latitude, double longitude) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.serviceSchedules = serviceSchedules;
        this.basePrice = basePrice;
        this.status = status;
        this.imageUrls = imageUrls;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
