package org.leniv.distributed.systems.cinema.repository;

import org.leniv.distributed.systems.cinema.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> getByName(String name);
}
