package com.project.withpet.repository.Order;

import com.project.withpet.domain.Ordertable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Ordertable, Long> {
    Ordertable save(Ordertable order);

    Optional<Ordertable> findByUserid(Long userid);


    List<Ordertable> findAllByOrderdateBetween(LocalDateTime start, LocalDateTime end);

}
