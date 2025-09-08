package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

