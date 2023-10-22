package org.leniv.distributed.systems.store.mapper;

import org.leniv.distributed.systems.store.dto.OrderCreationDto;
import org.leniv.distributed.systems.store.dto.OrderDto;
import org.leniv.distributed.systems.store.entity.Order;
import org.leniv.distributed.systems.store.entity.User;
import org.leniv.distributed.systems.store.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(uses = UserMapper.class)
public abstract class OrderMapper {
    @Autowired
    protected ProductRepository productRepository;

    public abstract OrderDto orderToOrderDto(Order order);

    public abstract List<OrderDto> ordersToOrderDtos(List<Order> orders);

    public abstract OrderDto.EntryDto entryToEntryDto(Order.Entry entry);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "paid", ignore = true)
    @Mapping(target = "total", ignore = true)
    public abstract Order orderCreationDtoToOrder(OrderCreationDto orderCreation, User user);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "paid", ignore = true)
    @Mapping(target = "total", ignore = true)
    public abstract Order orderCreationDtoToOrder(Long id, OrderCreationDto orderCreation, User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "product", expression = "java(productRepository.findById(entryCreation.getProductId()).orElseThrow())")
    public abstract Order.Entry entryCreationDtoToEntry(OrderCreationDto.EntryCreationDto entryCreation);
}
