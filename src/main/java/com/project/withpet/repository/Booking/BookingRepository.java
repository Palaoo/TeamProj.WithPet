package com.project.withpet.repository.Booking;

import com.project.withpet.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking save(Booking booking);
    List<Booking> findAllByUserid(String userid);
<<<<<<< HEAD
=======

    List<Booking> findAllByRoomid(Long roomid);
>>>>>>> f447d949d36c3e792afcf631f3469f4c6e448ae6
}
