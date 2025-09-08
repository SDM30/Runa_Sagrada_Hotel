package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

