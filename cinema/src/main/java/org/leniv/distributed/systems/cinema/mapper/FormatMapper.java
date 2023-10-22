package org.leniv.distributed.systems.cinema.mapper;

import org.leniv.distributed.systems.cinema.entity.Format;
import org.leniv.distributed.systems.cinema.repository.FormatRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class FormatMapper {
    @Autowired
    protected FormatRepository repository;

    public String formatToString(Format format) {
        return format.getName();
    }

    public Format stringToFormat(String format) {
        return repository.getByName(format).orElseGet(() -> repository.save(new Format(format)));
    }
}
