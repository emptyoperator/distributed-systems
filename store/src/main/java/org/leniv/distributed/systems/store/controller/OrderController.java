package org.leniv.distributed.systems.store.controller;

import lombok.AllArgsConstructor;
import org.leniv.distributed.systems.store.dto.OrderCreationDto;
import org.leniv.distributed.systems.store.dto.OrderDto;
import org.leniv.distributed.systems.store.dto.PaymentCardDto;
import org.leniv.distributed.systems.store.entity.Order;
import org.leniv.distributed.systems.store.entity.Product;
import org.leniv.distributed.systems.store.entity.User;
import org.leniv.distributed.systems.store.exception.InsufficientProductStockLevelException;
import org.leniv.distributed.systems.store.exception.OrderNotFoundException;
import org.leniv.distributed.systems.store.exception.PaidOrderModificationException;
import org.leniv.distributed.systems.store.exception.ProductNotFoundException;
import org.leniv.distributed.systems.store.mapper.OrderMapper;
import org.leniv.distributed.systems.store.repository.OrderRepository;
import org.leniv.distributed.systems.store.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
class OrderController {
    ProductRepository productRepository;
    OrderRepository orderRepository;
    OrderMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    OrderDto findById(@PathVariable Long id) {
        return orderRepository.findById(id).map(mapper::orderToOrderDto).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<OrderDto> findAll() {
        return mapper.ordersToOrderDtos(orderRepository.findAll());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    List<OrderDto> findMy(@AuthenticationPrincipal User user) {
        return mapper.ordersToOrderDtos(orderRepository.findAllByUser(user));
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    OrderDto place(@RequestBody OrderCreationDto orderCreation, @AuthenticationPrincipal User user) {
        updateProductsStockLevel(orderCreation);
        return mapper.orderToOrderDto(orderRepository.save(mapper.orderCreationDtoToOrder(orderCreation, user)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    OrderDto update(@PathVariable Long id, @RequestBody OrderCreationDto orderCreation, @AuthenticationPrincipal User user) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        if (existingOrder.isPaid()) {
            throw new PaidOrderModificationException(id);
        }
        restoreProductsStockLevel(existingOrder);
        updateProductsStockLevel(orderCreation);
        return mapper.orderToOrderDto(orderRepository.save(mapper.orderCreationDtoToOrder(id, orderCreation, user)));
    }

    @PostMapping("/{id}/pay")
    @PreAuthorize("hasRole('CUSTOMER')")
    OrderDto pay(@PathVariable Long id, @RequestBody PaymentCardDto paymentCard) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setPaid(true);
        return mapper.orderToOrderDto(orderRepository.save(order));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    void cancel(@PathVariable Long id) {
        orderRepository.findById(id).ifPresent(order -> {
            if (order.isPaid()) {
                throw new PaidOrderModificationException(id);
            }
            restoreProductsStockLevel(order);
        });
        orderRepository.deleteById(id);
    }

    void updateProductsStockLevel(OrderCreationDto orderCreation) {
        List<Product> updatedProducts = orderCreation.getEntries().stream()
                .map(entry -> {
                    Product product = productRepository.findById(entry.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException(entry.getProductId()));
                    long newProductQuantity = product.getQuantity() - entry.getQuantity();
                    if (newProductQuantity < 0) {
                        throw new InsufficientProductStockLevelException(product.getId());
                    }
                    product.setQuantity(newProductQuantity);
                    return product;
                }).toList();
        productRepository.saveAll(updatedProducts);
    }

    void restoreProductsStockLevel(Order order) {
        List<Product> restoredProducts = order.getEntries().stream()
                .map(entry -> {
                    Product product = entry.getProduct();
                    product.setQuantity(product.getQuantity() + entry.getQuantity());
                    return product;
                }).toList();
        productRepository.saveAll(restoredProducts);
    }
}
