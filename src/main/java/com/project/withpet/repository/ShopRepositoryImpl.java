//import com.project.withpet.domain.Shop;
//import com.project.withpet.repository.ShopRepositoryCustom;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//import static com.project.withpet.domain.QShop.shop;
//
//@RequiredArgsConstructor
//public class ShopRepositoryImpl implements ShopRepositoryCustom{
//    private final JPAQueryFactory queryFactory;
//
//
//    @Override
//    public List<Shop> findByName(String name) {
//        return queryFactory.selectFrom(shop)
//                .where(shop.name.eq(name))
//                .fetch();
//    }
//}