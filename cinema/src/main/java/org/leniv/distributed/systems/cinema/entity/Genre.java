package org.leniv.distributed.systems.cinema.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @ManyToMany
    private Set<Film> films;

    public Genre(String name) {
        this.name = name;
    }
}
