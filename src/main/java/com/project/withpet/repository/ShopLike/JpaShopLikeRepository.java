package com.project.withpet.repository.ShopLike;

import com.project.withpet.domain.shoplike;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaShopLikeRepository implements ShopLikeRepository {
    private final EntityManager em;

    public JpaShopLikeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(shoplike shoplike) {
        em.persist(shoplike);
    }

    @Override
    public List<shoplike> findByUserid(String userId) {
        return em.createQuery("select sl from shoplike sl where sl.userid= :userId")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<shoplike> findByShopid(Long shopId) {
        return em.createQuery("select sl from shoplike sl where sl.shopid= :shopId")
                .setParameter("shopId", shopId).getResultList();
    }

    @Override
    public Optional<shoplike> findByUseridAndShopid(String userId, Long shopId) {
        List<shoplike> resultList = em.createQuery("select sl from shoplike sl where sl.shopid=:shopId and sl.userid=:userId")
                .setParameter("shopId", shopId).setParameter("userId", userId)
                .getResultList();

        return resultList.stream().findAny();
    }

    @Override
    public void delete(String userId, Long shopId) {
        em.createQuery("delete from shoplike sl where sl.userid=:userId and sl.shopid=:shopId")
                .setParameter("userId", userId).setParameter("shopId", shopId)
                .executeUpdate();
    }
}
