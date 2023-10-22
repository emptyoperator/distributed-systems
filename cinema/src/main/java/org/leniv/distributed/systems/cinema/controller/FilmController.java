package org.leniv.distributed.systems.cinema.controller;

import lombok.AllArgsConstructor;
import org.leniv.distributed.systems.cinema.dto.FilmDto;
import org.leniv.distributed.systems.cinema.mapper.FilmMapper;
import org.leniv.distributed.systems.cinema.repository.FilmRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/films")
@AllArgsConstructor
class FilmController {
    FilmRepository repository;
    FilmMapper mapper;

    @GetMapping("/{id}")
    ResponseEntity<FilmDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id).map(mapper::filmToFilmDto));
    }

    @GetMapping
    List<FilmDto> findAll() {
        return mapper.filmsToFilmDtos(repository.findAll());
    }

    @PostMapping
    FilmDto create(@RequestBody FilmDto film) {
        return save(film);
    }

    @PutMapping("/{id}")
    ResponseEntity<FilmDto> update(@PathVariable Long id, @RequestBody FilmDto film) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        film.setId(id);
        return ResponseEntity.ok(save(film));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    FilmDto save(FilmDto film) {
        return mapper.filmToFilmDto(repository.save(mapper.filmDtoToFilm(film)));
    }
}
