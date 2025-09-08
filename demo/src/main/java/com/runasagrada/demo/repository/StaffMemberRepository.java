package com.runasagrada.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runasagrada.demo.entities.StaffMember;

public interface StaffMemberRepository extends JpaRepository<StaffMember, Long> {
}

