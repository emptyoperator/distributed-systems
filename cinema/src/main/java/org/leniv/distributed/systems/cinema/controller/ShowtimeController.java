package org.leniv.distributed.systems.cinema.controller;

import lombok.AllArgsConstructor;
import org.leniv.distributed.systems.cinema.dto.ShowtimeDto;
import org.leniv.distributed.systems.cinema.mapper.ShowtimeMapper;
import org.leniv.distributed.systems.cinema.repository.ShowtimeRepository;
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
@RequestMapping("/showtimes")
@AllArgsConstructor
class ShowtimeController {
    ShowtimeRepository repository;
    ShowtimeMapper mapper;

    @GetMapping("/{id}")
    ResponseEntity<ShowtimeDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(repository.findById(id).map(mapper::showtimeToShowtimeDto));
    }

    @GetMapping
    List<ShowtimeDto> findAll() {
        return mapper.showtimesToShowtimeDtos(repository.findAll());
    }

    @PostMapping
    ShowtimeDto create(@RequestBody ShowtimeDto showtime) {
        return save(showtime);
    }

    @PutMapping("/{id}")
    ResponseEntity<ShowtimeDto> update(@PathVariable Long id, @RequestBody ShowtimeDto showtime) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        showtime.setId(id);
        return ResponseEntity.ok(save(showtime));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    ShowtimeDto save(ShowtimeDto showtime) {
        return mapper.showtimeToShowtimeDto(repository.save(mapper.showtimeDtoToShowtime(showtime)));
    }
}
