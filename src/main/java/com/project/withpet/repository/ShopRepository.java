package com.project.withpet.repository;

import com.project.withpet.domain.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopRepository {

    List<Shop> findAll();

    List<Shop> findAllByshoptypeTypeid(Long typeid);

//    List<Shop> findByShopFeats_Featid(Long featid);

    Optional<Shop> findById(Long id);



}