package com.project.withpet.repository.ProdReview;

import com.project.withpet.domain.ProdReview;

import java.util.List;
import java.util.Optional;

public interface ProdReviewRepository  {
    ProdReview save(ProdReview prodReview);

    Optional<ProdReview> findByProdIdAndUserId(Long prodId,String userId);

    List<ProdReview> findByUserId(String userId);

    List<ProdReview> findByProdId(Long prodId);

    int deleteByProdIdAndUserId(Long prodId, String userId);
}
