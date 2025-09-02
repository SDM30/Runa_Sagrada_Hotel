package com.runasagrada.demo.service;

import java.util.Collection;

import com.runasagrada.demo.entities.ServiceOffering;

public interface ServiceOfferingService {
    public ServiceOffering searchById(Long id);

    public Collection<ServiceOffering> getAllServices();

    public void save(ServiceOffering service);

    public void delete(Long id);
}
