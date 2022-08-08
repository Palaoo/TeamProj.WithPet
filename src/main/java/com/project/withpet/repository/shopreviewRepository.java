package com.project.withpet.repository;

import com.project.withpet.domain.Shopreview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface shopreviewRepository extends JpaRepository<Shopreview, Long> {

    @Override
    List<Shopreview> findAll();

    List<Shopreview> findByShopid(Long shopid);

//    List<shopreview> findByUserid(String userid);
//    Optional<shopreview> findByUserid(String userid);

    List<Shopreview> findByUserid(String userid);
    Optional<Shopreview> findById(Long rid);

    @Query("select avg(sr.score) from Shopreview sr where sr.shopid= :shopid")
    Double getAvgByShopid(@Param("shopid") Long shopid);


    void deleteByShopid(Long shopid);

    Long findCountByShopid(Long shopid);

}