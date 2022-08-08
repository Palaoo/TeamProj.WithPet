package com.project.withpet.repository.ShopLike;

import com.project.withpet.domain.shoplike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeShopRepository extends JpaRepository<shoplike, Long> {

    void deleteByShopid(Long shopid);
}
