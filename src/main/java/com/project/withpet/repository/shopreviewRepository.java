package com.project.withpet.repository;

import com.project.withpet.domain.shopreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface shopreviewRepository extends JpaRepository<shopreview, Long> {

    @Override
    List<shopreview> findAll();

    List<shopreview> findByShopid(Long shopid);

//    List<shopreview> findByUserid(String userid);
//    Optional<shopreview> findByUserid(String userid);
    List<shopreview> findByUserid(String userid);
    Optional<shopreview> findById(Long rid);

}