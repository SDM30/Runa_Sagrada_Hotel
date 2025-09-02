package com.runasagrada.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runasagrada.demo.entities.ServiceOffering;
import com.runasagrada.demo.repository.ServiceOfferingRepository;

@Service
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    @Autowired
    private ServiceOfferingRepository serviceRepository;

    @Override
    public ServiceOffering searchById(Long id) {
        return serviceRepository.findById(id).get();
    }

    @Override
    public Collection<ServiceOffering> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public void save(ServiceOffering service) {
        serviceRepository.save(service);
    }

    @Override
    public void delete(Long id) {
        serviceRepository.deleteById(id);
    }

}
