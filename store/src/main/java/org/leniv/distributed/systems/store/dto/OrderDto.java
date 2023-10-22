package org.leniv.distributed.systems.store.dto;

import lombok.Data;
import org.leniv.distributed.systems.store.entity.Product;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private UserDto user;
    private Set<EntryDto> entries;
    private boolean paid;
    private BigDecimal total;

    @Data
    public static class EntryDto {
        private Product product;
        private long quantity;
        private BigDecimal total;
    }
}
