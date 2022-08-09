package com.project.withpet.repository;

import com.project.withpet.domain.Hotelroomimg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelroomimgRepository extends JpaRepository<Hotelroomimg, Long> {

    Optional<Hotelroomimg> findByShopid(Long roomid);

    void deleteByShopid(Long shopid);
}
