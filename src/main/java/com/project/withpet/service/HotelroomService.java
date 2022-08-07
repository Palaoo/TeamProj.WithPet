package com.project.withpet.service;

import com.project.withpet.domain.Hotelroom;
import com.project.withpet.repository.Hotelroom.HotelroomRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class HotelroomService {
    private final HotelroomRepository hotelroomRepository;

    public HotelroomService(HotelroomRepository hotelroomRepository) {
        this.hotelroomRepository = hotelroomRepository;
    }

    public List<Hotelroom> findByShopid(Long shopid){
        return hotelroomRepository.findByShopidOrderByPriceAsc(shopid);
    }

    public Optional<Hotelroom> cheapRoom(Long shopid){
        return hotelroomRepository.findTop1ByShopidOrderByPriceAsc(shopid);
    }

    public Optional<Hotelroom> findById(Long roomid){
        return hotelroomRepository.findById(roomid);
    }

    public Hotelroom save(Hotelroom hotelroom){
        return hotelroomRepository.save(hotelroom);
    }
}
