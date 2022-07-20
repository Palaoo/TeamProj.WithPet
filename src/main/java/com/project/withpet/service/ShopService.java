package com.project.withpet.service;

import com.project.withpet.domain.Shop;
import com.project.withpet.repository.ShopRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> shopList(){
        return shopRepository.findAll();
    }
}
