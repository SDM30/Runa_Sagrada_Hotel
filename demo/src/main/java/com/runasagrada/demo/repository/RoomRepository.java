package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.runasagrada.demo.entities.Room;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
        List<Room> findByNameContainingIgnoreCase(String name);
}
