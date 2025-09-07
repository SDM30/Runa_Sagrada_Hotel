package com.runasagrada.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "PaymentMethod")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "method_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private MethodType type;

    @Column(name = "last4", length = 4)
    private String last4;

    @Column(name = "holder_name", length = 120)
    private String holderName;

    @Column(name = "billing_address", length = 255)
    private String billingAddress;

    public enum MethodType { CARD, PAYPAL, CASH }

    public PaymentMethod() {}
}

