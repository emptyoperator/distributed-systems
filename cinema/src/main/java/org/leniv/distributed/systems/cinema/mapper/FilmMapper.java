package org.leniv.distributed.systems.cinema.mapper;

import org.leniv.distributed.systems.cinema.dto.FilmDto;
import org.leniv.distributed.systems.cinema.entity.Film;
import org.leniv.distributed.systems.cinema.repository.FilmRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(uses = {GenreMapper.class, FormatMapper.class, TechnologyMapper.class})
public abstract class FilmMapper {
    @Autowired
    protected FilmRepository repository;

    public abstract FilmDto filmToFilmDto(Film film);

    public abstract List<FilmDto> filmsToFilmDtos(List<Film> films);

    public abstract Film filmDtoToFilm(FilmDto film);

    public Film longToFilm(Long id) {
        return repository.getReferenceById(id);
    }
}
