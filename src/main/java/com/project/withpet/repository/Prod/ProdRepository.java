package com.project.withpet.repository.Prod;

import com.project.withpet.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProdRepository {
    Product save(Product prod);
    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);
}
