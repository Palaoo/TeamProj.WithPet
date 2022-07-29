package com.project.withpet.repository.Shop;

import com.project.withpet.domain.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.withpet.domain.QBooking.*;
import static com.project.withpet.domain.QHotelroom.*;
import static com.project.withpet.domain.QShop.*;

@Repository
public class ShopQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ShopQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

//    public List<Shop> findAvailHotel(String checkin, String checkout, Long person){
//        return queryFactory
//                .selectFrom(shop)
//                .where(shop.shopid.in(
//                        JPAExpressions.select(hotelroom.shopid)
//                                .from(hotelroom)
//                                .where(hotelroom.person.goe(person), hotelroom.roomid.notIn(
//                                        JPAExpressions.select(booking.roomid)
//                                                .from(booking)
//                                                .where(booking.checkin.between(checkin, checkout)
//                                                        .or(booking.checkout.between(checkin, checkout))
//                                                        .or(booking.checkin.loe(checkin)), booking.checkout.goe(checkout)
//                                                )
//                                        )
//                                )
//                        )
//                )
//                .fetch();
//    }

    public List<Shop> findAvailHotel(String checkin, String checkout, Long person){
        return queryFactory
                .selectFrom(shop)
                .where(shop.shopid
                        .in(JPAExpressions.select(hotelroom.shopid)
                                .from(hotelroom)
                                .where(hotelroom.person.goe(person)
                                        .and(hotelroom.roomid
                                                .notIn(JPAExpressions.select(booking.roomid)
                                                        .from(booking)
                                                        .where(booking.checkin.between(checkin, checkout)
                                                                .or(booking.checkout.between(checkin, checkout)
                                                                        .or(booking.checkin.loe(checkin)
                                                                                .and(booking.checkout.goe(checkout))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .fetch();
    }

    public List<Hotelroom> findAvailRoom(String checkin, String checkout, Long person, Long shopid){
        return queryFactory
                .selectFrom(hotelroom)
                .where(hotelroom.person.goe(person)
                        .and(hotelroom.shopid.eq(shopid)
                                .and(hotelroom.roomid
                                        .notIn(JPAExpressions.select(booking.roomid)
                                                .from(booking)
                                                .where(booking.checkin.between(checkin, checkout)
                                                        .or(booking.checkout.between(checkin, checkout)
                                                                .or(booking.checkin.loe(checkin)
                                                                        .and(booking.checkout.goe(checkout))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .fetch();
    }

//    public List<Hotelroom> findAvailRoom(String checkin, String checkout, Long person, Long shopid){
//        return queryFactory
//                .selectFrom(hotelroom)
//                .where(hotelroom.shopid.eq(shopid), hotelroom.person.goe(person), hotelroom.roomid.notIn(
//                        JPAExpressions.select(booking.roomid)
//                                .from(booking)
//                                .where(booking.checkin.between(checkin, checkout)
//                                        .or(booking.checkout.between(checkin, checkout))
//                                        .or(booking.checkin.loe(checkin)), booking.checkout.goe(checkout)
//                                )
//                        )
//                )
//                .fetch();
//    }



}
