package com.project.withpet.repository.Like;

import com.project.withpet.domain.Like;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaLikeRepository implements LikeRepository {
    private final EntityManager em;

    public JpaLikeRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Like save(Like like) {
        em.persist(like);
        return like;
    }

    @Override
    public void delete(Like like) {
        Like like1 = em.find(Like.class, like.getId());
        System.out.printf("From JpaLikeRepository delete(), like1.getId(): %d", like1.getId());
        em.remove(like1);
    }

    @Override
    public Long findCountByProdId(Long prodId) {
        String query = "select l from Like l where l.prodId= :prodId";

        return em.createQuery(query, Like.class).setParameter("prodId", prodId).getResultStream().count();
    }

    @Override
    public boolean isLike(Long prodId, String userId) {
        String query = "select l from Like l where l.prodId=:prodId and l.userId=:userId";
        if (em.createQuery(query, Like.class).setParameter("prodId", prodId).setParameter("userId", userId)
                .getResultList() == null) {
            return false;
        } else
            return true;
    }


    @Override
    public Optional<Like> findByProdIdandUserId(Long prodId, String userId) {
        List<Like> result = em.createQuery("select l from Like l where l.userId= :userId and l.prodId= :prodId")
                .setParameter("userId", userId).setParameter("prodId", prodId)
                .getResultList();

        return result.stream().findAny();
    }


}
