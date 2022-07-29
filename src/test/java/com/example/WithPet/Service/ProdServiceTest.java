package com.example.WithPet.Service;

import com.project.withpet.domain.Product;
import com.project.withpet.service.ProdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProdServiceTest {
    @Autowired
    ProdService prodService;

    @Test
    void findOne() {
        Product product = prodService.findById(19L).get();
        System.out.println(product.getName());
    }
}