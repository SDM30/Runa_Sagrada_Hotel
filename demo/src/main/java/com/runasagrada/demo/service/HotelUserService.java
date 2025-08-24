package com.runasagrada.demo.service;

import java.util.Collection;

import com.runasagrada.demo.entities.HotelUser;

public interface HotelUserService {

    public HotelUser searchById(Long id);

    public HotelUser searchByEmail(String email);

    public Collection<HotelUser> searchAll();

    // Update and create user
    public void save(HotelUser user);

    public void delete(Long id);
}
