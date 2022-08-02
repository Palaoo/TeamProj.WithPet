package com.project.withpet.repository.LikeHotel;

import com.project.withpet.domain.Like;
import com.project.withpet.domain.LikeHotel;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaLikeHotelRepository implements LikeHotelRepository {
    private final EntityManager em;

    public JpaLikeHotelRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public LikeHotel save(LikeHotel likeHotel) {
        em.persist(likeHotel);
        return null;
    }

    @Override
    public void delete(LikeHotel likeHotel) {
        LikeHotel likeHotel1 = em.find(LikeHotel.class, likeHotel.getId());
        em.remove(likeHotel1);
    }

    @Override
    public Long findCountByShopId(Long shopId) {
        return em.createQuery("select lh from LikeHotel lh where lh.shopId= :shopId")
                .setParameter("shopId", shopId).getResultStream().count()
                ;
    }

    @Override
    public boolean isLike(Long shopId, String userId) {
        String query = "select lh from LikeHotel lh where lh.shopId=:shopId and lh.userId=:userId";
        if (em.createQuery(query, LikeHotel.class).setParameter("shopId", shopId).setParameter("userId", userId)
                .getResultList() == null) {
            return false;
        } else
            return true;
    }

    @Override
    public Optional<LikeHotel> findByShopIdandUserId(Long shopId, String userId) {
        List<LikeHotel> result = em.createQuery("select lh from LikeHotel lh where lh.userId= :userId and lh.shopId= :shopId")
                .setParameter("userId", userId).setParameter("shopId", shopId)
                .getResultList();

        return result.stream().findAny();
    }
}
