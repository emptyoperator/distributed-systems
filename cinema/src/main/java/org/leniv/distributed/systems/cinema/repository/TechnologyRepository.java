package org.leniv.distributed.systems.cinema.repository;

import org.leniv.distributed.systems.cinema.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Optional<Technology> getByName(String name);
}
