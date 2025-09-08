package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.ServiceRating;

public interface ServiceRatingRepository extends JpaRepository<ServiceRating, Long> {
}

