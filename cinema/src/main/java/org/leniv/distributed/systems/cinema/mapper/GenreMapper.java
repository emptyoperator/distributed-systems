package org.leniv.distributed.systems.cinema.mapper;

import org.leniv.distributed.systems.cinema.entity.Genre;
import org.leniv.distributed.systems.cinema.repository.GenreRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class GenreMapper {
    @Autowired
    protected GenreRepository repository;

    public String genreToString(Genre genre) {
        return genre.getName();
    }

    public Genre stringToGenre(String genre) {
        return repository.getByName(genre).orElseGet(() -> repository.save(new Genre(genre)));
    }
}
