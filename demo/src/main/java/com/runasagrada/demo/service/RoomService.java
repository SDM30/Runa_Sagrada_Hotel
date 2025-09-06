package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    List<Room> search(String roomNumber,
                      Integer floorNumber,
                      Room.ReservationStatus resStatus,
                      Room.CleaningStatus cleStatus,
                      String themeName);

    Page<Room> search(String roomNumber,
                      Integer floorNumber,
                      Room.ReservationStatus resStatus,
                      Room.CleaningStatus cleStatus,
                      String themeName,
                      Pageable pageable);

    Room getById(Long id);

    boolean existsRoomNumber(String roomNumber);

    Room create(Room room);

    Room update(Long id, Room updated);

    void delete(Long id);
}
