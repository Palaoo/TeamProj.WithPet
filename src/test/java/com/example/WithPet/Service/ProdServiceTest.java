package com.example.WithPet.Service;

import com.example.WithPet.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProdServiceTest {
    @Autowired
    ProdService prodService;

    @Test
    void findOne() {
        Product product = prodService.findById(19L);
        System.out.println(product.getName());
    }
}