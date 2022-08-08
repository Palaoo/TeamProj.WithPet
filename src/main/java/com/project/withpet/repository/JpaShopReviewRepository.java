//package com.project.withpet.repository;
//
//import com.project.withpet.domain.Shopreview;
//
//import javax.persistence.EntityManager;
//
//public class JpaShopReviewRepository implements shopreviewRepository{
//
//    private final EntityManager em;
//
//    public JpaShopReviewRepository(EntityManager em) {
//        this.em = em;
//    }
//
//    @Override
//    public Long findCountByShopId(Long shopid) {
//        String query = "select l from l shopreview l where l.shopid= :shopid";
//        return em.createQuery(query, Shopreview.class).setParameter("shopid",shopid).getResultStream().count();
//    }
//}
