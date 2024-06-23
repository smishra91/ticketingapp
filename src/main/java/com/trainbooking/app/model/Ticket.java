package com.trainbooking.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private double price = 20.0;
    private String source;
    private String destination;
    private String section;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;
}
