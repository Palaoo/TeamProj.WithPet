package com.example.WithPet.repository.Like;

import com.example.WithPet.domain.Like;
import org.springframework.beans.factory.annotation.Autowired;

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
        em.remove(like);
    }

    @Override
    public Long findCountByProdId(Long prodId) {
        String query = "select l from Like l where l.prodId= :prodId";

        return em.createQuery(query, Like.class).setParameter("prodId", prodId).getResultStream().count();
    }

    @Override
    public boolean isLike(Long prodId, String userId) {
        String query = "select l from Like l where l.prodId=:prodId and l.userId=:userId";
        if (em.createQuery(query, Like.class).setParameter("prodId", prodId).setParameter("userId", userId).getResultList() == null) {
            return false;
        } else
            return true;
    }


}
