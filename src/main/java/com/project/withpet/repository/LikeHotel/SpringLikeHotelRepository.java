package com.project.withpet.repository.LikeHotel;

import com.project.withpet.domain.LikeHotel;
import com.project.withpet.domain.shoplike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringLikeHotelRepository extends JpaRepository<LikeHotel, Long> {
    List<LikeHotel> findByUserId(String userid);
    void deleteByShopId(Long shopid);
}
