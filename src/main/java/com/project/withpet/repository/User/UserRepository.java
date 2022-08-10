package com.project.withpet.repository.User;

import com.project.withpet.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    List<User> findAll();

    void deleteByUserid(String id);

    Optional<User> findByKakaoEmail(String kakaoEmail);

    @Transactional
    @Modifying
    @Query("update User u set u.kakaoEmail= :kakaoEmail where u.userid= :userId")
    void updateKakaoEmail(@Param("userId") String userId, @Param("kakaoEmail") String kakaoEmail);
}
