package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

