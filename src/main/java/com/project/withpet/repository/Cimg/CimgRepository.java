package com.project.withpet.repository.Cimg;

import com.project.withpet.domain.Cimg;

import java.util.List;
import java.util.Optional;

public interface CimgRepository {
    Cimg save(Cimg cimg);

    Optional<Cimg> findById(Long id);

    Optional<Cimg> findByName(String name);

    List<Cimg> findByProdid(Long prodid);

    List<Cimg> findAll();
}
