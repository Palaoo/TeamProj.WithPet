package god.withpet.repository;

import god.withpet.entity.region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface regionRepository extends JpaRepository<region, Long> {


}
