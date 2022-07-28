package com.example.WithPet.repository.ProdReview;

import com.example.WithPet.domain.ProdReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ProdReviewRepository  {
    ProdReview save(ProdReview prodReview);

    Optional<ProdReview> findByProdIdAndUserId(Long prodId,String userId);

    List<ProdReview> findByUserId(String userId);

    List<ProdReview> findByProdId(Long prodId);

    int deleteByProdIdAndUserId(Long prodId, String userId);
}
