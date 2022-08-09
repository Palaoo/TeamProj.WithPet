package com.project.withpet.repository.Prod;

import com.project.withpet.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProdRepository extends JpaRepository<Product, Long> {
    Product save(Product prod);

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);

    @Query("select p from Product p where p.bid= :bid")
    Page<Product> findAllByBid(Pageable pageable, @Param("bid") Long bid);

    void deleteById(Long id);

    @Transactional
    @Modifying
    @Query("update Product p set p.detail =:detail, p.name= :name, p.price= :price, p.type= :type where p.id= :prodId")
    void update(@Param("prodId") Long prodId, @Param("detail") String detail, @Param("name") String name,
                   @Param("price") int price, @Param("type") int type);
}
