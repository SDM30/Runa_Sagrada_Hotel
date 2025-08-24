package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
