package com.example.WithPet.repository.ProdReview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaProdReviewRepositoryTest {
    @Autowired
    ProdReviewRepository prodReviewRepository;


    @Test
    void deleteByProdIdAndUserId() {
        //given
        Long prodId = 30L;
        String userId = "cjh70105";

        //when
        int result = prodReviewRepository.deleteByProdIdAndUserId(prodId, userId);

        //then
        System.out.println("테스트 결과 : " + result);

    }
}