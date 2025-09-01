package com.runasagrada.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import com.runasagrada.demo.entities.ServiceOffering;
import com.runasagrada.demo.entities.ServiceSchedule;

public interface ServiceScheduleService {
    Collection<ServiceSchedule> findByService(ServiceOffering service);
    Collection<ServiceSchedule> searchByDate(LocalDate date);
    Collection<ServiceSchedule> searchByTime(LocalTime time);
    void save(ServiceSchedule schedule);
    void delete(Long id);
    void seedSchedules(ServiceSchedule baseSchedule, int days);
}
