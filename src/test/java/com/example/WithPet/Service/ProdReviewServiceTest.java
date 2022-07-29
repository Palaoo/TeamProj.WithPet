package com.example.WithPet.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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