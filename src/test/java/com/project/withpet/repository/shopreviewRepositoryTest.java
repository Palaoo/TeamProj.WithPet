package com.project.withpet.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

class shopreviewRepositoryTest {
    @Autowired
    shopreviewRepository shopreviewRepository;

//    shopreviewRepositoryTest(shopreviewRepository shopreviewRepository) {
//        this.shopreviewRepository = shopreviewRepository;
//    }


    @Test
    void getAvgByShopid() {
        Long shopId = 13L;

        double avgByShopid = shopreviewRepository.getAvgByShopid(shopId);

        Assertions.assertThat(avgByShopid).isEqualTo(4.5);
    }
}