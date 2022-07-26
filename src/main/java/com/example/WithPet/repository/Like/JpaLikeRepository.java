package com.example.WithPet.repository.Like;

import com.example.WithPet.domain.Like;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class JpaLikeRepository implements LikeRepository{
    @Autowired
    EntityManager em;


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
    public int findCountByProdId(Long prodId) {
        String query = "select count(l) from Like l where l.prodId= :prodId";
        return em.createQuery(query,Like.class).setParameter("prodId",prodId).getResultList().size();
    }

    @Override
    public boolean isLike(Long prodId,String userId) {
        String query = "select l from Like l where l.prodId=:prodId and l.userId=:userId";
        if (em.createQuery(query, Like.class).setParameter("prodId", prodId).setParameter("userId", userId).getResultList() == null) {
            return false;
        }else
            return true;
    }


}
