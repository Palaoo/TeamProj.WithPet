package com.project.withpet.repository;

import com.project.withpet.domain.Shop;
import com.querydsl.core.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaShopRepositoryCustom extends JpaRepository<Shop, Long>, ShopRepositoryCustom {

    @Override
    default List<Tuple> findAll() {
        return null;
    }
}
