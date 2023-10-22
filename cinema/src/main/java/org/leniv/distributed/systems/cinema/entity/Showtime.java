package org.leniv.distributed.systems.cinema.entity;

import static java.time.temporal.ChronoUnit.MINUTES;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Showtime {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Film film;

    @ManyToOne
    private Theater theater;

    @ManyToOne
    private Format format;

    @Column
    private Instant startTime;

    @Column
    private int availableSeats;

    public Instant getEndTime() {
        return startTime.plus(film.getDurationInMinutes(), MINUTES);
    }
}
