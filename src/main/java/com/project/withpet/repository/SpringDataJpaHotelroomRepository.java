package com.project.withpet.repository;

import com.project.withpet.domain.Hotelroom;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaHotelroomRepository extends JpaRepository<Hotelroom, Long>, HotelroomRepository {

}
