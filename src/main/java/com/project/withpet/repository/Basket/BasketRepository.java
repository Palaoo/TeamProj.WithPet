package com.project.withpet.repository.Basket;

import com.project.withpet.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket,Long> {
    List<Basket> findByUserid(String userid);

    Basket save(Basket basket);

    Optional<Basket> findByUseridAndProdid(String userid, Long prodid);
}
