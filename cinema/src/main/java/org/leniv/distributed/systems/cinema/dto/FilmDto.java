package org.leniv.distributed.systems.cinema.dto;

import lombok.Data;

import java.util.Set;

@Data
public class FilmDto {
    private Long id;
    private String title;
    private long releaseYear;
    private int durationInMinutes;
    private Set<String> genres;
    private Set<String> formats;
    private Set<String> technologies;
}
