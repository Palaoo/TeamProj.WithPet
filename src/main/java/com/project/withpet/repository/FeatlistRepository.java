package com.project.withpet.repository;

import com.project.withpet.domain.Featlist;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface FeatlistRepository extends JpaRepository<Featlist, Long> {
    @Transactional
    void deleteByShopid(Long shopid);
}
