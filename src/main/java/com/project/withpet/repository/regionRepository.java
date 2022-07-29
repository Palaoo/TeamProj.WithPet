package com.project.withpet.repository;

import com.project.withpet.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface regionRepository extends JpaRepository<Region, Long> {


}
