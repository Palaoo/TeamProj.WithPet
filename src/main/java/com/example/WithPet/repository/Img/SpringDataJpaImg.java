package com.example.WithPet.repository.Img;

import com.example.WithPet.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaImg extends JpaRepository<Img,Long>, ImgRepository {
}
