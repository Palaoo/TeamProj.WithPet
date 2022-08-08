package com.project.withpet.repository.Hotelroom;

import com.project.withpet.domain.Hotelroom;

import java.util.List;
import java.util.Optional;

public interface HotelroomRepository {

    List<Hotelroom> findByShopidOrderByPriceAsc(Long shopid);

    Optional<Hotelroom> findTop1ByShopidOrderByPriceAsc(Long shopid);

    Optional<Hotelroom> findById(Long roomid);

<<<<<<< HEAD
=======
    Hotelroom save(Hotelroom hotelroom);

>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
