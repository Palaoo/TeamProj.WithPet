package com.example.WithPet.repository.Order;

import com.example.WithPet.domain.Ordertable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Ordertable, Long> {
    Ordertable save(Ordertable order);

    Optional<Ordertable> findByUserid(Long userid);


    List<Ordertable> findAllByOrderdateBetween(LocalDateTime start, LocalDateTime end);

}
