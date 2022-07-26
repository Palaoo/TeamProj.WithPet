package com.example.WithPet.Service;

import com.example.WithPet.domain.Basket;
import com.example.WithPet.repository.Basket.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public List<Basket> findByUserid(String userid){
        return basketRepository.findByUserid(userid);
    }
}
