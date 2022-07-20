package com.project.withpet.repository;

import com.project.withpet.domain.Shop;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.transaction.Transactional;
import java.util.List;

import static com.project.withpet.domain.QRegion.*;
import static com.project.withpet.domain.QShop.shop;
import static com.project.withpet.domain.QShoptype.shoptype;


@Transactional
public class ShopRepositoryImpl extends QuerydslRepositorySupport implements ShopRepositoryCustom {

    public ShopRepositoryImpl() {
        super(Shop.class);
    }


    @Override
    public List<Tuple> findAll() {
        return queryFactory
                .select(shop.shopid, shop.name, shop.intro, shop.hour,
                        shop.info, shop.address, shop.tel, region.regname, shoptype.typename)
                .from(shop, region, shoptype)
                .where(shop.regid.eq(region.regid))
                .where(shop.typeid.eq(shoptype.typeid))
                .fetch();
    }
}
