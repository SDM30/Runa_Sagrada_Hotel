package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomTypeService {

    List<RoomType> search(String name, Integer minOccupancy, Integer maxOccupancy);

    Page<RoomType> search(String name, Integer minOccupancy, Integer maxOccupancy, Pageable pageable);

    List<RoomType> findAll();

    RoomType getById(Long id);

    boolean existsByName(String name);

    RoomType create(RoomType roomType);

    RoomType update(Long id, RoomType updated);

    void delete(Long id);
}
