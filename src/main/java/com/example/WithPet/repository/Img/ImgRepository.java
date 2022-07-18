package com.example.WithPet.repository.Img;

import com.example.WithPet.domain.Img;

import java.util.List;
import java.util.Optional;

public interface ImgRepository {
    Img save(Img img);

    Optional<Img> findById(Long id);

    Optional<Img> findByName(String name);

    Optional<Img> findByProdid(Long prodid);

    List<Img> findAll();
}
