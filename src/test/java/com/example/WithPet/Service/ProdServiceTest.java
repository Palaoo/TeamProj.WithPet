package com.example.WithPet.Service;

import com.example.WithPet.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ProdServiceTest {
    ProdService prodService;

    @Test
    void findOne() {
        Product product = prodService.findOne( 19L).get();
        System.out.println(product.getName());
    }
}