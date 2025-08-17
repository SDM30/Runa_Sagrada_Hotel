package com.runasagrada.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runasagrada.demo.entities.HotelService;
import com.runasagrada.demo.repository.HotelServiceRepository;

@Service
public class HotelServiceServiceImpl implements HotelServiceService {

    @Autowired
    private HotelServiceRepository serviceRepository;

    @Override
    public HotelService searchById(Integer id) {
        return serviceRepository.getServiceById(id);
    }

    @Override
    public Collection<HotelService> getAllServices() {
        return serviceRepository.getAllServices();
    }

    @Override
    public void save(HotelService service) {
        serviceRepository.save(service);
    }

    @Override
    public void delete(Integer id) {
        serviceRepository.delete(id);
    }

}
