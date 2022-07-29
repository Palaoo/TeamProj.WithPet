package com.project.withpet.repository;

import com.project.withpet.domain.cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface cafeRepository extends JpaRepository<cafe, Long> {

    @Override
    List<cafe> findAll();


    List<cafe> findAllByShoptypeTypeid(Long typeid);

}
