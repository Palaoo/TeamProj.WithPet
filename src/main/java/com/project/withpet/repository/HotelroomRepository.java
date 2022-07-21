package com.project.withpet.repository;

import com.project.withpet.domain.Hotelroom;

import java.util.List;

public interface HotelroomRepository {

    List<Hotelroom> findByShopid(Long shopid);
}
