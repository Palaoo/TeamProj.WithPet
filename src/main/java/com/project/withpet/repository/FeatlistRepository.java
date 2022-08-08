package com.project.withpet.repository;

import com.project.withpet.domain.Featlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatlistRepository extends JpaRepository<Featlist, Long> {
    void deleteByShopid(Long shopid);
}
