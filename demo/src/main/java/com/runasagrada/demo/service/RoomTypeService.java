package com.runasagrada.demo.service;

import com.runasagrada.demo.entities.Room;
import com.runasagrada.demo.entities.RoomType;
import com.runasagrada.demo.repository.RoomRepository;
import com.runasagrada.demo.repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    
    @Autowired
    private RoomRepository roomRepository;

    public RoomTypeService(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    // Crear nuevo tipo de habitación
    public RoomType create(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }

    // Listar todos
    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }

    public Optional<RoomType> findById(Long id) {
        return roomTypeRepository.findById(id);
    }

    public RoomType update(Long id, RoomType updatedRoomType) {
        return roomTypeRepository.findById(id)
                .map(existingRoomType -> {
                    existingRoomType.setName(updatedRoomType.getName());
                    existingRoomType.setCapacity(updatedRoomType.getCapacity());
                    existingRoomType.setBasePrice(updatedRoomType.getBasePrice());
                    existingRoomType.setDescription(updatedRoomType.getDescription());
                    return roomTypeRepository.save(existingRoomType);
                })
                .orElseThrow(() -> new RuntimeException("RoomType not found with id " + id));
    }

    public void delete(Long id) {
        // Check if RoomType exists
        if (!roomTypeRepository.existsById(id)) {
            throw new RuntimeException("RoomType not found with id " + id);
        }
        
        // Check if there are rooms associated with this room type
        List<Room> associatedRooms = roomRepository.findByRoomTypeId(id);
        if (!associatedRooms.isEmpty()) {
            throw new RuntimeException("Cannot delete room type. There are " + associatedRooms.size() + " rooms associated with this room type.");
        }
        
        roomTypeRepository.deleteById(id);
    }

    public List<RoomType> search(String name, Integer minCapacity, Integer maxCapacity) {
        return roomTypeRepository.findAll().stream()
                .filter(roomType -> (name == null || roomType.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(roomType -> (minCapacity == null || roomType.getCapacity() >= minCapacity))
                .filter(roomType -> (maxCapacity == null || roomType.getCapacity() <= maxCapacity))
                .toList();
    }
}
