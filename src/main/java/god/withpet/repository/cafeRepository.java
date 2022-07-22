package god.withpet.repository;

import god.withpet.entity.cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface cafeRepository extends JpaRepository<cafe, Long> {

    @Override
    List<cafe> findAll();


}
