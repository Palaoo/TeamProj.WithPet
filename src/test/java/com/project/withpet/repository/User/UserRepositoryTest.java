package com.project.withpet.repository.User;

import com.project.withpet.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {
    UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void findByKakaoEmail() {
        //given
        String kakaoEmail = "cjh70109@daum.net";

        //when
        Optional<User> result = userRepository.findByKakaoEmail(kakaoEmail);

        //then

        Assertions.assertThat(result.get().getUserId()).isEqualTo("cjh70104");
    }
}