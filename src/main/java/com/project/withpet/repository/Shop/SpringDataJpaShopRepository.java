package com.project.withpet.repository.Shop;

import com.project.withpet.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaShopRepository extends JpaRepository<Shop, Long>, ShopRepository {

}
