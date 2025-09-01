package com.runasagrada.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runasagrada.demo.entities.ServiceOffering;
import com.runasagrada.demo.entities.ServiceSchedule;
import com.runasagrada.demo.repository.ServiceScheduleRepository;

@Service
public class ServiceScheduleServiceImpl implements ServiceScheduleService {

    @Autowired
    private ServiceScheduleRepository scheduleRepository;

    @Override
    public Collection<ServiceSchedule> findByService(ServiceOffering service) {
        return scheduleRepository.findByService(service);
    }

    @Override
    public Collection<ServiceSchedule> searchByDate(LocalDate date) {
        return scheduleRepository.findBySchedDate(date);
    }

    @Override
    public Collection<ServiceSchedule> searchByTime(LocalTime time) {
        return scheduleRepository.findByStartTime(time);
    }

    @Override
    public void save(ServiceSchedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public void seedSchedules(ServiceSchedule baseSchedule, int days) {
        ServiceOffering service = baseSchedule.getService();
        LocalDate baseDate = baseSchedule.getSchedDate();
        LocalTime startTime = baseSchedule.getStartTime();
        LocalTime endTime = baseSchedule.getEndTime();
        boolean isActive = true; // default to active when seeding

        for (int i = 0; i < days; i++) {
            ServiceSchedule newSchedule = new ServiceSchedule(
                    service,
                    baseDate.plusDays(i),
                    startTime,
                    endTime,
                    isActive);
            scheduleRepository.save(newSchedule);
        }
    }

}
