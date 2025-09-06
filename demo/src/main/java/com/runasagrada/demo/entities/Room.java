package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "Room") // matches your INSERTs
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // simple numeric FK to a hotel; no Hotel entity required for now
    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    @Column(name = "room_number", length = 20, nullable = false)
    private String roomNumber;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "res_status", length = 20, nullable = false)
    private ReservationStatus resStatus;

    public enum ReservationStatus {
        AVAILABLE,
        BOOKED,
        OCCUPIED,
        MAINTENANCE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "cle_status", length = 20, nullable = false)
    private CleaningStatus cleStatus;

    public enum CleaningStatus {
        CLEAN,
        DIRTY,
        IN_PROGRESS
    }

    @Column(name = "theme_name", length = 120)
    private String themeName;

    @Column(name = "theme_description", length = 500)
    private String themeDescription;

    public Room() {
    }
}
