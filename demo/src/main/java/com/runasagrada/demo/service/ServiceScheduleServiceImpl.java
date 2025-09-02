package com.runasagrada.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runasagrada.demo.entities.HotelService;
import com.runasagrada.demo.entities.ServiceSchedule;
import com.runasagrada.demo.repository.ServiceScheduleRepository;

@Service
public class ServiceScheduleServiceImpl implements ServiceScheduleService {

    @Autowired
    private ServiceScheduleRepository scheduleRepository;

    @Override
    public Collection<ServiceSchedule> searchByCapacity(int capacity) {
        return scheduleRepository.findByCapacity(capacity);
    }

    @Override
    public Collection<ServiceSchedule> searchByDate(LocalDate date) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByDate'");
    }

    @Override
    public Collection<ServiceSchedule> searchByTime(LocalTime time) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchByTime'");
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
        HotelService service = baseSchedule.getService();
        LocalDate baseDate = baseSchedule.getSched_date();
        LocalTime time = baseSchedule.getSched_time();
        int capacity = baseSchedule.getCapacity();

        for (int i = 0; i < days; i++) {
            ServiceSchedule newSchedule = new ServiceSchedule(
                    service,
                    baseDate.plusDays(i),
                    time,
                    capacity);
            scheduleRepository.save(newSchedule);
        }
    }

}
