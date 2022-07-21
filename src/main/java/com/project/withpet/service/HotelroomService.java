package com.project.withpet.service;

import com.project.withpet.domain.Hotelroom;
import com.project.withpet.repository.HotelroomRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class HotelroomService {
    private final HotelroomRepository hotelroomRepository;

    public HotelroomService(HotelroomRepository hotelroomRepository) {
        this.hotelroomRepository = hotelroomRepository;
    }

    public List<Hotelroom> findByShopid(Long shopid){
        return hotelroomRepository.findByShopid(shopid);
    }
}
