package com.runasagrada.demo.repository;

import com.runasagrada.demo.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

       @Query("""
                     select rt from RoomType rt
                     where (:name is null or :name = '' or lower(rt.name) like lower(concat('%', :name, '%')))
                       and (:minOcc is null or rt.maxOccupancy >= :minOcc)
                       and (:maxOcc is null or rt.maxOccupancy <= :maxOcc)
                     order by rt.id desc
                     """)
       List<RoomType> search(
                     @Param("name") String name,
                     @Param("minOcc") Integer minOccupancy,
                     @Param("maxOcc") Integer maxOccupancy);

       @Query(value = """
                     select rt from RoomType rt
                     where (:name is null or :name = '' or lower(rt.name) like lower(concat('%', :name, '%')))
                       and (:minOcc is null or rt.maxOccupancy >= :minOcc)
                       and (:maxOcc is null or rt.maxOccupancy <= :maxOcc)
                     """, countQuery = """
                     select count(rt) from RoomType rt
                     where (:name is null or :name = '' or lower(rt.name) like lower(concat('%', :name, '%')))
                       and (:minOcc is null or rt.maxOccupancy >= :minOcc)
                       and (:maxOcc is null or rt.maxOccupancy <= :maxOcc)
                     """)
       Page<RoomType> search(
                     @Param("name") String name,
                     @Param("minOcc") Integer minOccupancy,
                     @Param("maxOcc") Integer maxOccupancy,
                     Pageable pageable);

       boolean existsByNameIgnoreCase(String name);
}
