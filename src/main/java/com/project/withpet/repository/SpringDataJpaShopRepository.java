package com.project.withpet.repository;

import com.project.withpet.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaShopRepository extends JpaRepository<Shop, Long>, ShopRepository {
}
