package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.runasagrada.demo.entities.HotelService;

@Repository
public interface HotelServiceRepository extends JpaRepository<HotelService, Long> {

}