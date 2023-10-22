package org.leniv.distributed.systems.cinema.mapper;

import org.leniv.distributed.systems.cinema.dto.ShowtimeDto;
import org.leniv.distributed.systems.cinema.entity.Showtime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = {FilmMapper.class, TheaterMapper.class, FormatMapper.class})
public interface ShowtimeMapper {
    @Mapping(target = "filmId", source = "film.id")
    @Mapping(target = "theaterId", source = "theater.id")
    ShowtimeDto showtimeToShowtimeDto(Showtime showtime);

    List<ShowtimeDto> showtimesToShowtimeDtos(List<Showtime> showtimes);

    @Mapping(target = "film", source = "filmId")
    @Mapping(target = "theater", source = "theaterId")
    Showtime showtimeDtoToShowtime(ShowtimeDto showtime);
}
