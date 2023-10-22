package org.leniv.distributed.systems.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Theater {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int totalSeats;

    @ManyToOne
    private Technology technology;
}
