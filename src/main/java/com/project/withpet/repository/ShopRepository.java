package com.project.withpet.repository;

import com.project.withpet.domain.Shop;

import java.util.List;

public interface ShopRepository {

    List<Shop> findAll();

}
