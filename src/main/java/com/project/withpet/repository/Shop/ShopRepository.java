package com.project.withpet.repository.Shop;

import com.project.withpet.domain.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopRepository {

    List<Shop> findAll();

    List<Shop> findAllByshoptypeTypeid(Long typeid);

//    List<Shop> findByShopFeats_Featid(Long featid);

    Optional<Shop> findById(Long id);


<<<<<<< HEAD

=======
    List<Shop> findByAddressContainingAndShoptypeTypeid(String keyword, Long typeid);

    Shop save(Shop shop);

    List<Shop> findByBidAndShoptypeTypeid(Long bid, Long typeid);

    List<Shop> findAllByBid(Long bid);

    void deleteById(Long shopid);
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
