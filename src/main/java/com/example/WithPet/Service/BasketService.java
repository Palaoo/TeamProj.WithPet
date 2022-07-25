package com.example.WithPet.Service;

import com.example.WithPet.domain.Basket;
import com.example.WithPet.repository.Basket.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class BasketService {
    @Autowired
    EntityManager em;

    private final BasketRepository bascketRepository;

    public BasketService(BasketRepository bascketRepository) {
        this.bascketRepository = bascketRepository;
    }

    public List<Basket> findByUserid(String userid){
        return bascketRepository.findByUserid(userid);
    }
}
