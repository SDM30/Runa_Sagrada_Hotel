package com.runasagrada.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runasagrada.demo.entities.Client;
import com.runasagrada.demo.entities.HotelUser;
import com.runasagrada.demo.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client searchById(Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Collection<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> search(String email, String phone, String nationalId, String name) {
        if ((email == null || email.isBlank()) &&
                (phone == null || phone.isBlank()) &&
                (nationalId == null || nationalId.isBlank()) &&
                (name == null || name.isBlank())) {
            return clientRepository.findAll();
        }
        return clientRepository.findByCriteria(email, phone, nationalId, name);
    }

}
