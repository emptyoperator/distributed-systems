package org.leniv.distributed.systems.cinema.dto;

import lombok.Data;

@Data
public class TheaterDto {
    private Long id;
    private int totalSeats;
    private String technology;
}
