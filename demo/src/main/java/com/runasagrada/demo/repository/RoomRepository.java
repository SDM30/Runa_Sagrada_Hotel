package com.runasagrada.demo.repository;

import com.runasagrada.demo.entities.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            select r from Room r
            left join fetch r.roomType rt
            where (:roomNumber is null or :roomNumber = '' or lower(r.roomNumber) like lower(concat('%', :roomNumber, '%')))
              and (:floorNumber is null or r.floorNumber = :floorNumber)
              and (:resStatus is null or r.resStatus = :resStatus)
              and (:cleStatus is null or r.cleStatus = :cleStatus)
              and (:themeName is null or :themeName = '' or lower(r.themeName) like lower(concat('%', :themeName, '%')))
            order by r.id desc
            """)
    List<Room> search(
            @Param("roomNumber") String roomNumber,
            @Param("floorNumber") Integer floorNumber,
            @Param("resStatus") Room.ReservationStatus resStatus,
            @Param("cleStatus") Room.CleaningStatus cleStatus,
            @Param("themeName") String themeName);

    @Query(value = """
            select r from Room r
            left join fetch r.roomType rt
            where (:roomNumber is null or :roomNumber = '' or lower(r.roomNumber) like lower(concat('%', :roomNumber, '%')))
              and (:floorNumber is null or r.floorNumber = :floorNumber)
              and (:resStatus is null or r.resStatus = :resStatus)
              and (:cleStatus is null or r.cleStatus = :cleStatus)
              and (:themeName is null or :themeName = '' or lower(r.themeName) like lower(concat('%', :themeName, '%')))
            """, countQuery = """
            select count(r) from Room r
            where (:roomNumber is null or :roomNumber = '' or lower(r.roomNumber) like lower(concat('%', :roomNumber, '%')))
              and (:floorNumber is null or r.floorNumber = :floorNumber)
              and (:resStatus is null or r.resStatus = :resStatus)
              and (:cleStatus is null or r.cleStatus = :cleStatus)
              and (:themeName is null or :themeName = '' or lower(r.themeName) like lower(concat('%', :themeName, '%')))
            """)
    Page<Room> search(
            @Param("roomNumber") String roomNumber,
            @Param("floorNumber") Integer floorNumber,
            @Param("resStatus") Room.ReservationStatus resStatus,
            @Param("cleStatus") Room.CleaningStatus cleStatus,
            @Param("themeName") String themeName,
            Pageable pageable);

    @Query("select r from Room r left join fetch r.roomType where r.id = :id")
    Optional<Room> findByIdFetchType(@Param("id") Long id);

    boolean existsByRoomNumberIgnoreCase(String roomNumber);
}
