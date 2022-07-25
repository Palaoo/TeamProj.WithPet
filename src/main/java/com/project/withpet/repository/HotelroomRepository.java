package com.project.withpet.repository;

import com.project.withpet.domain.Hotelroom;

import java.util.List;
import java.util.Optional;

public interface HotelroomRepository {

    List<Hotelroom> findByShopidOrderByPriceAsc(Long shopid);

    Optional<Hotelroom> findTop1ByShopidOrderByPriceAsc(Long shopid);

}
