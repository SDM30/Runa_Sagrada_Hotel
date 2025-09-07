package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.repository.RoomRepository;
import com.runasagrada.demo.repository.HotelRepository;
import com.runasagrada.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Room> search(String roomNumber,
            Integer floorNumber,
            Room.ReservationStatus resStatus,
            Room.CleaningStatus cleStatus,
            String themeName) {
        return roomRepository.search(roomNumber, floorNumber, resStatus, cleStatus, themeName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> search(String roomNumber,
            Integer floorNumber,
            Room.ReservationStatus resStatus,
            Room.CleaningStatus cleStatus,
            String themeName,
            Pageable pageable) {
        return roomRepository.search(roomNumber, floorNumber, resStatus, cleStatus, themeName, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Room getById(Long id) {
        return roomRepository.findByIdFetchType(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsRoomNumber(String roomNumber) {
        return roomRepository.existsByRoomNumberIgnoreCase(roomNumber);
    }

    @Override
    @Transactional
    public Room create(Room room) {
        validateRoomBasics(room);
        attachRoomTypeReference(room);
        if (existsRoomNumber(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exists: " + room.getRoomNumber());
        }
        return roomRepository.save(room);
    }

    @Override
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

        current.setHotel(updated.getHotel());
        current.setRoomType(updated.getRoomType());
        current.setRoomNumber(updated.getRoomNumber());
        current.setFloorNumber(updated.getFloorNumber());
        current.setResStatus(updated.getResStatus());
        current.setCleStatus(updated.getCleStatus());
        current.setThemeName(updated.getThemeName());
        current.setThemeDescription(updated.getThemeDescription());

        return roomRepository.save(current);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            roomRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Cannot delete room " + id + " due to references.", ex);
        }
    }

    private void validateRoomBasics(Room room) {
        if (room.getHotel() == null)
            throw new IllegalArgumentException("HotelId is required.");
        if (!hotelRepository.existsById(room.getHotel().getId()))
            throw new IllegalArgumentException("HotelId not found: " + room.getHotel().getId());
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
