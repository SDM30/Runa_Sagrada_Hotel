package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "Room.withRoomType", attributeNodes = @NamedAttributeNode("roomType"))
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_id")
    private Long hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "res_status")
    private ReservationStatus resStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "cle_status")
    private CleaningStatus cleStatus;

    @Column(name = "theme_name")
    private String themeName;

    @Column(name = "theme_description", length = 500)
    private String themeDescription;

    // Enums for status
    public enum ReservationStatus {
        AVAILABLE, BOOKED, OCCUPIED, MAINTENANCE
    }

    public enum CleaningStatus {
        CLEAN, DIRTY, CLEANING, OUT_OF_ORDER
    }
}
