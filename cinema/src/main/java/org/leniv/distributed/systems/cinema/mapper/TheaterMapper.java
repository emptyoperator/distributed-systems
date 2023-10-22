package org.leniv.distributed.systems.cinema.mapper;

import org.leniv.distributed.systems.cinema.dto.TheaterDto;
import org.leniv.distributed.systems.cinema.entity.Theater;
import org.leniv.distributed.systems.cinema.repository.TheaterRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(uses = {FilmMapper.class, TechnologyMapper.class})
public abstract class TheaterMapper {
    @Autowired
    protected TheaterRepository repository;

    public abstract TheaterDto theaterToTheaterDto(Theater theater);

    public abstract List<TheaterDto> theatersToTheaterDtos(List<Theater> theaters);

    public abstract Theater theaterDtoToTheater(TheaterDto theater);

    public Theater longToTheater(Long id) {
        return repository.getReferenceById(id);
    }
}
