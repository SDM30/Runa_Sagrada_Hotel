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
    public HotelService searchById(Long id) {
        return serviceRepository.findById(id).get();
    }

    @Override
    public Collection<HotelService> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public void save(HotelService service) {
        serviceRepository.save(service);
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

}
