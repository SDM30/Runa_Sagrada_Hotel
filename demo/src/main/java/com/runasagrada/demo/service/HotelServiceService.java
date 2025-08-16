package com.runasagrada.demo.service;

import java.util.Collection;

import com.runasagrada.demo.entities.HotelService;

public interface HotelServiceService {
    public HotelService searchById(Integer id);

    public Collection<HotelService> getAllServices();

    public void save(HotelService service);

    public void delete(Integer id);
}
