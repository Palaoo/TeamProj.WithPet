package com.project.withpet.repository;


import com.project.withpet.domain.ShopForm;
import com.project.withpet.domain.Shop;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.withpet.domain.QRegion.*;
import static com.project.withpet.domain.QShop.shop;

@Repository
public class ShopQueryRepository {
    private final JPAQueryFactory queryFactory;

    public ShopQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

//    public Shop findByName(String name) {
//        return queryFactory.selectFrom(shop)
//                .where(shop.name.eq(name))
//                .fetchOne();
//    }
//
    public List<Shop> findShopInShop(){
        return queryFactory
                .selectFrom(shop)
                .fetch();

    }
//
//    public List<ShopForm> findShops(){
//        return queryFactory
//                .select(Projections.fields(ShopForm.class, shop))
//                .from(shop)
//                .fetch();
//
//    }

//    public List<Tuple> findAllShops(){
//        return queryFactory
////                .select(Projections.fields(ShopForm.class,
////                    shop.name, region.regname))
//                .select(shop.name, region.regname)
//                    .from(shop)
//                    .join(region).on(shop.regid.eq(region.regid))
//                    .fetch();
//    }

}
