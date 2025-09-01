package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ServiceSchedule {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ServiceOffering service;

    private LocalDate sched_date;
    private LocalTime sched_time;
    private int capacity;

    public ServiceSchedule() {
    }

    public ServiceSchedule(ServiceOffering service, LocalDate date, LocalTime time, int capacity) {
        this.service = service;
        this.sched_date = date;
        this.sched_time = time;
        this.capacity = capacity;
    }

}
