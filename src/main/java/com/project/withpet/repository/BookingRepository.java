package com.project.withpet.repository;

import com.project.withpet.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking save(Booking booking);
}
