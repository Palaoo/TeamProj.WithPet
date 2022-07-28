package com.example.WithPet.repository.ProdReview;

import com.example.WithPet.domain.ProdReview;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaProdReviewRepository implements ProdReviewRepository {
    private final EntityManager em;

    public JpaProdReviewRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public ProdReview save(ProdReview prodReview) {
        em.persist(prodReview);
        return prodReview;
    }

    @Override
    public Optional<ProdReview> findByProdIdAndUserId(Long prodId, String userId) {
        List resultList = em.createQuery("select pr from ProdReview pr where pr.prodId= :prodId and pr.userId= :userId")
                .setParameter("prodId", prodId).setParameter("userId", userId)
                .getResultList();
        return resultList.stream().findAny();
    }

    @Override
    public List<ProdReview> findByUserId(String userId) {
        return em.createQuery("select pr from ProdReview pr where pr.userId= :userId")
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<ProdReview> findByProdId(Long prodId) {
        return em.createQuery("select pr from ProdReview pr where pr.prodId= :prodId")
                .setParameter("prodId", prodId).getResultList();
    }

    @Override
    public int deleteByProdIdAndUserId(Long prodId, String userId) {
        int i = em.createQuery("delete from ProdReview pr where pr.prodId= :prodId and pr.userId= :userId")
                .setParameter("prodId", prodId).setParameter("userId", userId).executeUpdate();

        return i;   // 삭제 성공시 1 리턴
    }
}
