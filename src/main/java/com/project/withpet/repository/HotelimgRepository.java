package com.project.withpet.repository;

import com.project.withpet.domain.Hotelimg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelimgRepository  extends JpaRepository<Hotelimg, Long> {
    Optional<Hotelimg> findByShopid(Long shopid);
    void deleteByShopid(Long shopid);
}
