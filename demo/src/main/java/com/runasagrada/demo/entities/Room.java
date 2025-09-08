package com.runasagrada.demo.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference("hotel-rooms")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RoomLock> roomLocks = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reservation> reservations = new ArrayList<>();

    // Expose hotelId for JSON while keeping hotel back-reference hidden
    @JsonProperty("hotelId")
    public Long getHotelId() {
        return hotel != null ? hotel.getId() : null;
    }

    public Room() {
    }
}
