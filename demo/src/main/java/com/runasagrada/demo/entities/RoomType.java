package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Nombre de la habitación
    private Integer capacity; // Capacidad (número de personas)
    private Double basePrice; // Precio base
    private String description; // Descripción (ya se la saben)
}
