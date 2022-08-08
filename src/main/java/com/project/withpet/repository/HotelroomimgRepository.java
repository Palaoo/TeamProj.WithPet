package com.project.withpet.repository;

import com.project.withpet.domain.Hotelroomimg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface HotelroomimgRepository extends JpaRepository<Hotelroomimg, Long> {

    Optional<Hotelroomimg> findByShopid(Long roomid);

    @Transactional
    void deleteByShopid(Long shopid);
}
