package org.leniv.distributed.systems.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Film {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    private long releaseYear;

    @Column
    private int durationInMinutes;

    @ManyToMany
    private Set<Genre> genres;

    @ManyToMany
    private Set<Format> formats;

    @ManyToMany
    private Set<Technology> technologies;
}
