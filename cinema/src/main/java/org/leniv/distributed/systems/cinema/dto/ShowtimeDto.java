package org.leniv.distributed.systems.cinema.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ShowtimeDto {
    private Long id;
    private Long filmId;
    private Long theaterId;
    private String format;
    private Instant startTime;
    private Instant endTime;
    private int availableSeats;
}
