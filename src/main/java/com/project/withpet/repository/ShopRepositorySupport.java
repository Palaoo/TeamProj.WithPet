//package com.project.withpet.repository;
//
//import com.project.withpet.domain.Shop;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static com.project.withpet.domain.QShop.shop;
//
//@Repository
//public class ShopRepositorySupport extends QuerydslRepositorySupport {
//
//    private final JPAQueryFactory queryFactory;
//
//    public ShopRepositorySupport(JPAQueryFactory queryFactory) {
//        super(Shop.class);
//        this.queryFactory = queryFactory;
//    }
//
//    public List<Shop> findByName(String name){
//        return queryFactory.selectFrom(shop)
//                .where(shop.name.eq(name))
//                .fetch();
//    }
//}
