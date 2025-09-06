package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class ServiceSchedule {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ServiceOffering service;

    private LocalDate schedDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean is_active;

    public ServiceSchedule() {
    }

    public ServiceSchedule(ServiceOffering service, LocalDate sched_date, LocalTime start_time, LocalTime end_time,
            boolean is_active) {
        this.service = service;
        this.schedDate = sched_date;
        this.startTime = start_time;
        this.endTime = end_time;
        this.is_active = is_active;
    }
}
