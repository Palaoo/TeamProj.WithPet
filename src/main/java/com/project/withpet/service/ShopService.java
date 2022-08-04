package com.project.withpet.service;

import com.project.withpet.domain.Shop;
import com.project.withpet.domain.cafe;
import com.project.withpet.repository.Shop.ShopRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> shopList(){
        return shopRepository.findAll();
    }

    public Optional<Shop> findById(Long id){
        return shopRepository.findById(id);
    }

    public List<Shop> hotelList(Long typeid){
        return shopRepository.findAllByshoptypeTypeid(typeid);
    }

    public List<Shop> search(String keyword, Long typeid) {
        return shopRepository.findByAddressContainingAndShoptypeTypeid(keyword,typeid);
    }

}
