package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.runasagrada.demo.entities.HotelUser;
import java.util.List;

@Repository
public interface HotelUserRepository extends JpaRepository<HotelUser, Long> {
    HotelUser findByEmail(String email);
}
