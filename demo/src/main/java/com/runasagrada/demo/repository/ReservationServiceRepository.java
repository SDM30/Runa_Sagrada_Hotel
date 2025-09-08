package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.ReservationService;

public interface ReservationServiceRepository extends JpaRepository<ReservationService, Long> {
}

