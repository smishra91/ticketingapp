package com.trainbooking.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @Column(name = "section_a_capacity")
    private int sectionACapacity;
    @Column(name = "section_b_capacity")
    private int sectionBCapacity;
    private int availableSeatsSectionA;
    private int availableSeatsSectionB;

}
