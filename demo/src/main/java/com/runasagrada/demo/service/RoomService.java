package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Crear nueva habitación
    public Room create(Room room) {
        return roomRepository.save(room);
    }

    // Listar todas
    public List<Room> findAll() {
        return roomRepository.findAll();
    }


    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }


    public Room update(Long id, Room updatedRoom) {
        return roomRepository.findById(id)
                .map(existingRoom -> {
                    existingRoom.setName(updatedRoom.getName());
                    existingRoom.setCapacity(updatedRoom.getCapacity());
                    existingRoom.setBasePrice(updatedRoom.getBasePrice());
                    existingRoom.setDescription(updatedRoom.getDescription());
                    return roomRepository.save(existingRoom);
                })
                .orElseThrow(() -> new RuntimeException("Room not found with id " + id));
    }


    public void delete(Long id) {
        roomRepository.deleteById(id);
    }


    public List<Room> search(String name, Integer minCapacity, Integer maxCapacity) {
        return roomRepository.findAll().stream()
                .filter(room -> (name == null || room.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(room -> (minCapacity == null || room.getCapacity() >= minCapacity))
                .filter(room -> (maxCapacity == null || room.getCapacity() <= maxCapacity))
                .toList();
    }
}
