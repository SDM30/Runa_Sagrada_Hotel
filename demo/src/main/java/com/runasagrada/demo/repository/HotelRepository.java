package com.runasagrada.demo.repository;

import com.runasagrada.demo.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}

