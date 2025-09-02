package com.runasagrada.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.runasagrada.demo.entities.ServiceOffering;
import com.runasagrada.demo.entities.ServiceSchedule;

@Repository
public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {
    Collection<ServiceSchedule> findByService(ServiceOffering service);

    Collection<ServiceSchedule> findBySchedDate(LocalDate date);

    Collection<ServiceSchedule> findByStartTime(LocalTime time);
}
