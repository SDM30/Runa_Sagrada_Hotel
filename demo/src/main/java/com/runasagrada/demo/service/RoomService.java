package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.repository.RoomRepository;
import com.runasagrada.demo.repository.RoomTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    public RoomService(RoomRepository roomRepository, RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<Room> search(String roomNumber,
            Integer floorNumber,
            Room.ReservationStatus resStatus,
            Room.CleaningStatus cleStatus,
            String themeName) {
        return roomRepository.search(roomNumber, floorNumber, resStatus, cleStatus, themeName);
    }

    @Transactional(readOnly = true)
    public Page<Room> search(String roomNumber,
            Integer floorNumber,
            Room.ReservationStatus resStatus,
            Room.CleaningStatus cleStatus,
            String themeName,
            Pageable pageable) {
        return roomRepository.search(roomNumber, floorNumber, resStatus, cleStatus, themeName, pageable);
    }

    @Transactional(readOnly = true)
    public Room getById(Long id) {
        return roomRepository.findByIdFetchType(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + id));
    }

    @Transactional(readOnly = true)
    public boolean existsRoomNumber(String roomNumber) {
        return roomRepository.existsByRoomNumberIgnoreCase(roomNumber);
    }

    @Transactional
    public Room create(Room room) {
        validateRoomBasics(room);
        attachRoomTypeReference(room);
        if (existsRoomNumber(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exists: " + room.getRoomNumber());
        }
        return roomRepository.save(room);
    }

    @Transactional
    public Room update(Long id, Room updated) {
        validateRoomBasics(updated);
        Room current = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + id));

        if (!current.getRoomNumber().equalsIgnoreCase(updated.getRoomNumber())
                && existsRoomNumber(updated.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exists: " + updated.getRoomNumber());
        }

        attachRoomTypeReference(updated);

        current.setHotelId(updated.getHotelId());
        current.setRoomType(updated.getRoomType());
        current.setRoomNumber(updated.getRoomNumber());
        current.setFloorNumber(updated.getFloorNumber());
        current.setResStatus(updated.getResStatus());
        current.setCleStatus(updated.getCleStatus());
        current.setThemeName(updated.getThemeName());
        current.setThemeDescription(updated.getThemeDescription());

        return roomRepository.save(current);
    }

    @Transactional
    public void delete(Long id) {
        try {
            roomRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Cannot delete room " + id + " due to references.", ex);
        }
    }

    private void validateRoomBasics(Room room) {
        if (room.getHotelId() == null)
            throw new IllegalArgumentException("HotelId is required.");
        if (room.getRoomNumber() == null || room.getRoomNumber().isBlank())
            throw new IllegalArgumentException("Room number is required.");
        if (room.getFloorNumber() == null)
            throw new IllegalArgumentException("Floor number is required.");
        if (room.getResStatus() == null)
            throw new IllegalArgumentException("Reservation status is required.");
        if (room.getCleStatus() == null)
            throw new IllegalArgumentException("Cleaning status is required.");
    }

    private void attachRoomTypeReference(Room room) {
        if (room.getRoomType() == null || room.getRoomType().getId() == null) {
            throw new IllegalArgumentException("RoomType is required.");
        }
        RoomType rtRef = roomTypeRepository.getReferenceById(room.getRoomType().getId());
        room.setRoomType(rtRef);
    }
}
