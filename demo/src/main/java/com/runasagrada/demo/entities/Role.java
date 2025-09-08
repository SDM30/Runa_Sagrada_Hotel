package com.runasagrada.demo.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name", length = 20, unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<StaffMember> users;

    public Role() {
    }
}
