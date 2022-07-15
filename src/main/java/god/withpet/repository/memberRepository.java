package god.withpet.repository;

import god.withpet.entity.member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface memberRepository  extends JpaRepository<member, String> {

}
