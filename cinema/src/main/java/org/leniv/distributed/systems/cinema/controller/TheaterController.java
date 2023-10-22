package org.leniv.distributed.systems.cinema.controller;

import lombok.AllArgsConstructor;
import org.leniv.distributed.systems.cinema.dto.TheaterDto;
import org.leniv.distributed.systems.cinema.mapper.TheaterMapper;
import org.leniv.distributed.systems.cinema.repository.TheaterRepository;
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
@RequestMapping("/theaters")
@AllArgsConstructor
class TheaterController {
    TheaterRepository repository;
    TheaterMapper mapper;

    @GetMapping("/{id}")
    ResponseEntity<TheaterDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id).map(mapper::theaterToTheaterDto));
    }

    @GetMapping
    List<TheaterDto> findAll() {
        return mapper.theatersToTheaterDtos(repository.findAll());
    }

    @PostMapping
    TheaterDto create(@RequestBody TheaterDto theater) {
        return save(theater);
    }

    @PutMapping("/{id}")
    ResponseEntity<TheaterDto> update(@PathVariable Long id, @RequestBody TheaterDto theater) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        theater.setId(id);
        return ResponseEntity.ok(save(theater));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    TheaterDto save(TheaterDto theater) {
        return mapper.theaterToTheaterDto(repository.save(mapper.theaterDtoToTheater(theater)));
    }
}
