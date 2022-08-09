package com.example.WithPet.Service;

import com.project.withpet.service.ProdReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ProdReviewServiceTest {
    @Autowired
    ProdReviewService prodReviewService;



    @Test
    void 상품리뷰생성테스트() {
        Long aLong = prodReviewService.saveProdReview(30L, "cjh70105", "리뷰 테스트 글", 2);
        System.out.println(aLong + "\n");

        assertThat(aLong).isEqualTo(prodReviewService.findByProdIdAndUserId(30L, "cjh70105").getReviewId());
    }
}