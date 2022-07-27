package god.withpet.repository;

import god.withpet.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository {

    User save(User user);
    Optional<User> findById(String userid);

}
