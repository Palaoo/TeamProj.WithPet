package com.example.WithPet.repository.Basket;

import com.example.WithPet.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketRepository extends JpaRepository<Basket,Long> {
    List<Basket> findByUserid(String userid);

    Basket save(Basket basket);
}
