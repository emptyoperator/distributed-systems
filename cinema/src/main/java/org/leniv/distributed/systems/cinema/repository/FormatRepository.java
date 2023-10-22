package org.leniv.distributed.systems.cinema.repository;

import org.leniv.distributed.systems.cinema.entity.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long> {
    Optional<Format> getByName(String name);
}
