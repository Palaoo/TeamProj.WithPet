package com.project.withpet.repository;

import com.project.withpet.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {


     Region findByRegname(String regname);
}
