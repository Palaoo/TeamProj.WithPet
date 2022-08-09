package com.project.withpet.repository.Hotelroom;

import com.project.withpet.domain.Hotelroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaHotelroomRepository extends JpaRepository<Hotelroom, Long>, HotelroomRepository {

}
