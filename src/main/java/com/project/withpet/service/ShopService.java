package com.project.withpet.service;

import com.project.withpet.domain.Shop;
<<<<<<< HEAD
=======
import com.project.withpet.domain.cafe;
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
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

<<<<<<< HEAD
=======
    public List<Shop> search(String keyword, Long typeid) {
        return shopRepository.findByAddressContainingAndShoptypeTypeid(keyword,typeid);
    }

    public Long save(Shop shop){
        return shopRepository.save(shop).getShopid();
    }

    public List<Shop> findHotelByBid(Long bid){
        return shopRepository.findByBidAndShoptypeTypeid(bid, 1L);
    }

    public List<Shop> findAllByBid(Long bid){
        return shopRepository.findAllByBid(bid);
    }

    public void deleteById(Long shopid){
        shopRepository.deleteById(shopid);
    }
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
