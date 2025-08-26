package com.runasagrada.demo.service;

import java.util.Collection;
import java.util.List;

import com.runasagrada.demo.entities.Client;

public interface ClientService {

    public Client searchById(Long id);

    public Collection<Client> getAllClients();

    // Create & update
    public void save(Client client);

    public void delete(Long id);

    public List<Client> search(String email, String phone, String nationalId, String name);

    public Client login(String email, String password);

}
