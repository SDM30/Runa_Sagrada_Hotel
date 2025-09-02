package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.repository.RoomRepository;
import com.runasagrada.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    // Create new room
    public Room create(Room room) {
        // Validate that room number doesn't exist on the same floor and hotel
        if (roomRepository.existsByRoomNumberAndFloorNumberAndHotelId(
                room.getRoomNumber(), room.getFloorNumber(), room.getHotelId())) {
            throw new RuntimeException("Room number already exists on this floor");
        }
        return roomRepository.save(room);
    }

    // Get all rooms
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    // Find room by ID
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    // Update room
    public Room update(Long id, Room updatedRoom) {
        return roomRepository.findById(id)
                .map(existingRoom -> {
                    // Check if room number change would create a conflict
                    if (!existingRoom.getRoomNumber().equals(updatedRoom.getRoomNumber()) ||
                        !existingRoom.getFloorNumber().equals(updatedRoom.getFloorNumber()) ||
                        !existingRoom.getHotelId().equals(updatedRoom.getHotelId())) {
                        
                        if (roomRepository.existsByRoomNumberAndFloorNumberAndHotelId(
                                updatedRoom.getRoomNumber(), updatedRoom.getFloorNumber(), updatedRoom.getHotelId())) {
                            throw new RuntimeException("Room number already exists on this floor");
                        }
                    }
                    
                    existingRoom.setHotelId(updatedRoom.getHotelId());
                    existingRoom.setRoomType(updatedRoom.getRoomType());
                    existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
                    existingRoom.setFloorNumber(updatedRoom.getFloorNumber());
                    existingRoom.setResStatus(updatedRoom.getResStatus());
                    existingRoom.setCleStatus(updatedRoom.getCleStatus());
                    existingRoom.setThemeName(updatedRoom.getThemeName());
                    existingRoom.setThemeDescription(updatedRoom.getThemeDescription());
                    return roomRepository.save(existingRoom);
                })
                .orElseThrow(() -> new RuntimeException("Room not found with id " + id));
    }

    // Delete room
    public void delete(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id " + id);
        }
        roomRepository.deleteById(id);
    }

    // Search rooms with multiple criteria
    public List<Room> search(String roomNumber, Integer floorNumber, 
                           Room.ReservationStatus resStatus, Room.CleaningStatus cleStatus,
                           Long hotelId, String themeName) {
        return roomRepository.findAll().stream()
                .filter(room -> roomNumber == null || room.getRoomNumber().toLowerCase().contains(roomNumber.toLowerCase()))
                .filter(room -> floorNumber == null || room.getFloorNumber().equals(floorNumber))
                .filter(room -> resStatus == null || room.getResStatus().equals(resStatus))
                .filter(room -> cleStatus == null || room.getCleStatus().equals(cleStatus))
                .filter(room -> hotelId == null || room.getHotelId().equals(hotelId))
                .filter(room -> themeName == null || (room.getThemeName() != null && room.getThemeName().toLowerCase().contains(themeName.toLowerCase())))
                .collect(Collectors.toList());
    }

    // Find rooms by room number
    public List<Room> findByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

    // Find rooms by floor number
    public List<Room> findByFloorNumber(Integer floorNumber) {
        return roomRepository.findByFloorNumber(floorNumber);
    }

    // Find rooms by reservation status
    public List<Room> findByReservationStatus(Room.ReservationStatus resStatus) {
        return roomRepository.findByResStatus(resStatus);
    }

    // Find rooms by cleaning status
    public List<Room> findByCleaningStatus(Room.CleaningStatus cleStatus) {
        return roomRepository.findByCleStatus(cleStatus);
    }

    // Find rooms by hotel ID
    public List<Room> findByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    // Find rooms by theme name
    public List<Room> findByThemeName(String themeName) {
        return roomRepository.findByThemeNameContainingIgnoreCase(themeName);
    }

    // Search rooms by room number, room type name, reservation status, and cleaning status
    public List<Room> searchRooms(String roomNumber, String roomTypeName, 
                                 Room.ReservationStatus reservationStatus, Room.CleaningStatus cleaningStatus) {
        return roomRepository.findAll().stream()
                .filter(room -> roomNumber == null || room.getRoomNumber().toLowerCase().contains(roomNumber.toLowerCase()))
                .filter(room -> roomTypeName == null || (room.getRoomType() != null && 
                        room.getRoomType().getName().toLowerCase().contains(roomTypeName.toLowerCase())))
                .filter(room -> reservationStatus == null || room.getResStatus().equals(reservationStatus))
                .filter(room -> cleaningStatus == null || room.getCleStatus().equals(cleaningStatus))
                .collect(Collectors.toList());
    }

    // Get all room types (for dropdown in forms)
    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }
}