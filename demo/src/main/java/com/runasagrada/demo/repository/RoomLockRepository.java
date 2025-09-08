package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.RoomLock;
import com.runasagrada.demo.entities.RoomLockId;

public interface RoomLockRepository extends JpaRepository<RoomLock, RoomLockId> {
}

