package com.project.withpet.repository.Hotelroom.Img;

import com.project.withpet.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaImg extends JpaRepository<Img,Long>, ImgRepository {
}
