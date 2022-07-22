package god.withpet.repository;

import god.withpet.entity.shopreview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface shopreviewRepository extends JpaRepository<shopreview, Long> {

    @Override
    List<shopreview> findAll();

    List<shopreview> findByShopid(Long shopid);
}
