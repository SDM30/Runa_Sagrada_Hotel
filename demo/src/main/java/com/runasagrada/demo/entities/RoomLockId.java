package com.runasagrada.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable // Compound Key
public class RoomLockId implements Serializable {

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "lock_date")
    private LocalDate lockDate;

    public RoomLockId() {}

    public RoomLockId(Long roomId, LocalDate lockDate) {
        this.roomId = roomId;
        this.lockDate = lockDate;
    }
}

