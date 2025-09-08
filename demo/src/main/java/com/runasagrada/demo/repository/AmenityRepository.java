package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}

