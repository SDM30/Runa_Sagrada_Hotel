package com.runasagrada.demo.service;

import java.util.Collection;

import com.runasagrada.demo.entities.Client;

public interface ClientService {

    public Client searchById(Long id);

    public Collection<Client> getAllClients();

    // Create & update
    public void save(Client client);

    public void delete(Long id);

}
