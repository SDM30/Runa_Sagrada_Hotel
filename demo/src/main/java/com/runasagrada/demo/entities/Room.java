package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
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

    public Room(Long id, Long hotelId, RoomType roomType, String roomNumber, Integer floorNumber,
            ReservationStatus resStatus, CleaningStatus cleStatus,
            String themeName, String themeDescription) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.resStatus = resStatus;
        this.cleStatus = cleStatus;
        this.themeName = themeName;
        this.themeDescription = themeDescription;
    }

    // getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public ReservationStatus getResStatus() {
        return resStatus;
    }

    public void setResStatus(ReservationStatus resStatus) {
        this.resStatus = resStatus;
    }

    public CleaningStatus getCleStatus() {
        return cleStatus;
    }

    public void setCleStatus(CleaningStatus cleStatus) {
        this.cleStatus = cleStatus;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeDescription() {
        return themeDescription;
    }

    public void setThemeDescription(String themeDescription) {
        this.themeDescription = themeDescription;
    }

    // equals & hashCode by id

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Room))
            return false;
        Room that = (Room) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Room{id=" + id + ", number='" + roomNumber + "'}";
    }
}
