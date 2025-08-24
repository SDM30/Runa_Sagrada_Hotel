package com.runasagrada.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runasagrada.demo.entities.HotelUser;
import com.runasagrada.demo.repository.HotelUserRepository;

@Service
public class HotelUserServiceImpl implements HotelUserService {

    @Autowired
    private HotelUserRepository userRepository;

    @Override
    public HotelUser searchById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public HotelUser searchByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Collection<HotelUser> searchAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(HotelUser user) {
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
