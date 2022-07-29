package com.project.withpet.repository.Img;

import com.project.withpet.domain.Img;

import java.util.List;
import java.util.Optional;

public interface ImgRepository {
    Img save(Img img);

    Optional<Img> findById(Long id);

    Optional<Img> findByName(String name);

    Optional<Img> findByProdid(Long prodid);

    List<Img> findAll();
}
