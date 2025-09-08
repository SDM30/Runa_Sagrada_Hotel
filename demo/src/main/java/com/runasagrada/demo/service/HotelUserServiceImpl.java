package com.runasagrada.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        if (user.getPassword() == null) {
            user.setPassword("12345678");
        }
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserFields(HotelUser updatedUser) {
        try {
            HotelUser user = userRepository.findById(updatedUser.getId()).get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setNationalId(updatedUser.getNationalId());

            if (updatedUser.getProfileIcon() != null)
                user.setProfileIcon(updatedUser.getProfileIcon());

            // Only update password if it is not null and not blank
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                user.setPassword(updatedUser.getPassword());
            }

            userRepository.save(user);
        } catch (Exception ex) {
            throw new DataIntegrityViolationException(
                    "No se pudo actualizar: el correo, teléfono o ID nacional ya está registrados.");
        }
    }
}
