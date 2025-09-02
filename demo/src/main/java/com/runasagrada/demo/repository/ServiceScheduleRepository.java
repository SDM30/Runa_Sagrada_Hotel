package com.runasagrada.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.runasagrada.demo.entities.ServiceSchedule;

@Repository
public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {

    Collection<ServiceSchedule> findByCapacity(int capacity);

}
