package com.project.withpet.service;

import com.project.withpet.domain.Product;
import com.project.withpet.repository.Prod.ProdRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ProdService {
    private final ProdRepository prodRepository;

    public ProdService(ProdRepository prodRepository) {
        this.prodRepository = prodRepository;
    }

    public Long save(Product prod) {
        return prodRepository.save(prod).getId();
    }

    public Page<Product> findProds(Pageable pageable) {
        return prodRepository.findAll(pageable);
    }

    public Product findByName(String prodName) {
        return prodRepository.findByName(prodName).get();
    }
    public Optional<Product> findById(Long id) {
        return prodRepository.findById(id);
    }

}
