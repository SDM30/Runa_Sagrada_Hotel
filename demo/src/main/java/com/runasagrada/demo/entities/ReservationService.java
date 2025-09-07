package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@Table(name = "ReservationService")
public class ReservationService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_service_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceOffering service;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schedule_id", nullable = false)
    private ServiceSchedule schedule;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private Status status;

    public enum Status { ORDERED, DELIVERED, CANCELED }

    public ReservationService() {}
}

