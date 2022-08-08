package com.project.withpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatlistRepository extends JpaRepository<Featlist, Long> {
    void deleteByShopid(Long shopid);
}
