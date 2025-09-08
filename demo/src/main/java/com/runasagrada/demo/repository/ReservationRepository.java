package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

