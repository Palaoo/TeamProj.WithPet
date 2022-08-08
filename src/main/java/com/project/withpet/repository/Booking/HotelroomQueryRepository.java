package com.project.withpet.repository.Booking;

import com.project.withpet.domain.Booking;
import com.project.withpet.domain.QBooking;
import com.project.withpet.domain.QHotelroom;
import com.project.withpet.domain.QShop;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.withpet.domain.QBooking.*;
import static com.project.withpet.domain.QHotelroom.*;
import static com.project.withpet.domain.QShop.*;

@Repository
public class HotelroomQueryRepository {

    private final JPAQueryFactory queryFactory;

    public HotelroomQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Booking> findBookingByBid(Long bid){
        return queryFactory
                .selectFrom(booking)
                .where(booking.roomid
                        .in(JPAExpressions.select(hotelroom.roomid)
                                .from(hotelroom)
                                .where(hotelroom.shopid
                                        .in(JPAExpressions.select(shop.shopid)
                                                .from(shop)
                                                .where(shop.bid.eq(bid))
                                        )
                                )
                        )
                )
                .fetch();
    }
}
