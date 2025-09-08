package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

