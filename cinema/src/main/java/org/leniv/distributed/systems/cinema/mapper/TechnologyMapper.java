package org.leniv.distributed.systems.cinema.mapper;

import org.leniv.distributed.systems.cinema.entity.Technology;
import org.leniv.distributed.systems.cinema.repository.TechnologyRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class TechnologyMapper {
    @Autowired
    protected TechnologyRepository repository;

    public String technologyToString(Technology technology) {
        return technology.getName();
    }

    public Technology stringToTechnology(String technology) {
        return repository.getByName(technology).orElseGet(() -> repository.save(new Technology(technology)));
    }
}
