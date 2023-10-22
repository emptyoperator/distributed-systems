package org.leniv.distributed.systems.cinema.repository;

import org.leniv.distributed.systems.cinema.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
}
