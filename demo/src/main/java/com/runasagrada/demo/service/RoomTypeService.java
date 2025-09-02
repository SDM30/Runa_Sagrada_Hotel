package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.repository.RoomTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    /*
     * =========================
     * Queries (read-only)
     * =========================
     */

    @Transactional(readOnly = true)
    public List<RoomType> search(String name, Integer minOccupancy, Integer maxOccupancy) {
        return roomTypeRepository.search(name, minOccupancy, maxOccupancy);
    }

    @Transactional(readOnly = true)
    public Page<RoomType> search(String name, Integer minOccupancy, Integer maxOccupancy, Pageable pageable) {
        return roomTypeRepository.search(name, minOccupancy, maxOccupancy, pageable);
    }

    @Transactional(readOnly = true)
    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RoomType getById(Long id) {
        return roomTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RoomType not found: " + id));
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roomTypeRepository.existsByNameIgnoreCase(name);
    }

    /*
     * =========================
     * Commands (write)
     * =========================
     */

    @Transactional
    public RoomType create(RoomType roomType) {
        validateBasics(roomType);
        if (existsByName(roomType.getName())) {
            throw new IllegalArgumentException("RoomType name already exists: " + roomType.getName());
        }
        return roomTypeRepository.save(roomType);
    }

    @Transactional
    public RoomType update(Long id, RoomType updated) {
        validateBasics(updated);
        RoomType current = getById(id);

        // If renaming, enforce uniqueness
        if (!current.getName().equalsIgnoreCase(updated.getName()) && existsByName(updated.getName())) {
            throw new IllegalArgumentException("RoomType name already exists: " + updated.getName());
        }

        current.setName(updated.getName());
        current.setDescription(updated.getDescription());
        current.setBasePrice(updated.getBasePrice());
        current.setMaxOccupancy(updated.getMaxOccupancy());
        current.setAmenities(updated.getAmenities());

        return roomTypeRepository.save(current);
    }

    @Transactional
    public void delete(Long id) {
        try {
            roomTypeRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            // Will trigger if there are Rooms pointing to this type
            throw new IllegalStateException("Cannot delete RoomType " + id + " because rooms depend on it.", ex);
        }
    }

    /*
     * =========================
     * Helpers
     * =========================
     */

    private void validateBasics(RoomType rt) {
        if (rt.getName() == null || rt.getName().isBlank()) {
            throw new IllegalArgumentException("RoomType name is required.");
        }
        if (rt.getBasePrice() == null) {
            throw new IllegalArgumentException("Base price is required.");
        }
        if (rt.getMaxOccupancy() == null) {
            throw new IllegalArgumentException("Max occupancy is required.");
        }
    }
}
