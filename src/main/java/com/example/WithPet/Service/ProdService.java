package com.example.WithPet.Service;

import com.example.WithPet.domain.Product;
import com.example.WithPet.repository.Prod.ProdRepository;
import com.example.WithPet.repository.Prod.SpringDataJpaProd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

    public Optional<Product> findOne(Long id) {
        return prodRepository.findById(id);
    }

}
