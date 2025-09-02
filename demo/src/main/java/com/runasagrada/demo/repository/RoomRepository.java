package com.runasagrada.demo.repository;

import com.runasagrada.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    // Find rooms by room number
    List<Room> findByRoomNumber(String roomNumber);
    
    // Find rooms by floor number
    List<Room> findByFloorNumber(Integer floorNumber);
    
    // Find rooms by reservation status
    List<Room> findByResStatus(Room.ReservationStatus resStatus);
    
    // Find rooms by cleaning status
    List<Room> findByCleStatus(Room.CleaningStatus cleStatus);
    
    // Find rooms by hotel ID
    List<Room> findByHotelId(Long hotelId);
    
    // Find rooms by theme name
    List<Room> findByThemeNameContainingIgnoreCase(String themeName);
    
    // Find rooms by room type
    List<Room> findByRoomTypeId(Long roomTypeId);
    
    // Check if room number exists on a specific floor and hotel
    boolean existsByRoomNumberAndFloorNumberAndHotelId(String roomNumber, Integer floorNumber, Long hotelId);
}