package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "staff_id", nullable = false)
    private StaffMember staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_service_id")
    private ReservationService reservationService;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15, nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum Type { DELIVERY, GUIDING, TO_DO }
    public enum Status { PENDING, IN_PROGRESS, DONE, CANCELED }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Task() {}
}

