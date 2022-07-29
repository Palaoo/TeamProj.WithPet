package com.project.withpet.service;

import com.project.withpet.domain.ProdReview;
import com.project.withpet.repository.ProdReview.ProdReviewRepository;

import java.util.List;

public class ProdReviewService {
    private final ProdReviewRepository prodReviewRepository;

    public ProdReviewService(ProdReviewRepository prodReviewRepository) {
        this.prodReviewRepository = prodReviewRepository;
    }

    public Long saveProdReview(Long prodId, String userId, String text, int star) throws IllegalStateException {
        validate(prodId, userId);

        ProdReview prodReview = new ProdReview(prodId, userId, text, star);
        prodReviewRepository.save(prodReview);
        return prodReview.getReviewId();
    }

    public ProdReview findByProdIdAndUserId(Long prodId, String userId) {
        return prodReviewRepository.findByProdIdAndUserId(prodId, userId).get();
    }

    public List<ProdReview> findByProdId(Long prodId) {
        return prodReviewRepository.findByProdId(prodId);
    }

    public List<ProdReview> findByUserId(String userId) {
        return prodReviewRepository.findByUserId(userId);
    }

    private void validate(Long prodId, String userId) {
        prodReviewRepository.findByProdIdAndUserId(prodId, userId)
                .ifPresent(pr -> {
                    throw new IllegalStateException("이미 작성한 리뷰가 존재합니다.");
                });
    }

    public int deleteProdReview(Long prodId,String userId) {
        try {
            validate(prodId, userId);
        } catch (IllegalStateException e) {
            return prodReviewRepository.deleteByProdIdAndUserId(prodId, userId);    // 삭제 성공시 리턴 1
        }
        return 0;   // 해당 Row 존재하지 않을시 0 리턴
    }
}
