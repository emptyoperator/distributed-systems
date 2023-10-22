package org.leniv.distributed.systems.store.repository;

import org.leniv.distributed.systems.store.entity.Order;
import org.leniv.distributed.systems.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
