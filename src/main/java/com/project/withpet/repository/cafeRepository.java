package com.project.withpet.repository;

import com.project.withpet.domain.cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;


import javax.transaction.Transactional;
import java.util.List;

public interface cafeRepository extends JpaRepository<cafe, Long> {

    @Override
    List<cafe> findAll();

    Page<cafe> findAll(Pageable pageable);

    @Query("select c from cafe c where c.shoptype= (select st.typeid from Shoptype st where st.typeid= :typeId)")
    Page<cafe> findCafe(Pageable pageable,@Param(value = "typeId") Long typeId);

    List<cafe> findAllByShoptypeTypeid(Long typeid);

    List<cafe> findByAddressContainingAndShoptypeTypeid(String keyword, Long typeid);
}
