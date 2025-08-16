package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelService {
    private Integer id;
    private String name;
    private String description;
    private LocalDate sheduledDate;
    private LocalTime scheduledTime;
    private int capacity;
    private double basePrice;
    private String status;
    private String imageUrl;
}
