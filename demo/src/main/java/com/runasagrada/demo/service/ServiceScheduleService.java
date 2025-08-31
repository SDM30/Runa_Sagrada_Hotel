package com.runasagrada.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import com.runasagrada.demo.entities.ServiceSchedule;

public interface ServiceScheduleService {
    public Collection<ServiceSchedule> searchByCapacity(int capacity);

    public Collection<ServiceSchedule> searchByDate(LocalDate date);

    public Collection<ServiceSchedule> searchByTime(LocalTime time);

    public void save(ServiceSchedule schedule);

    public void delete(Long id);

    public void seedSchedules(ServiceSchedule baseSchedule, int days);
}
