package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

