package com.project.withpet.service;

import com.project.withpet.domain.Board;
import com.project.withpet.domain.User;
import com.project.withpet.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception{
        User user = new User();
        user.setUserId("banana");
        user.setPassword("1234");
        user.setName("바나나");
        user.setMobile("01011112222");
        Optional<User> user1 = userService.join(user);
        boolean b = userService.checkUser(user1.get().getUserId(), user1.get().getPassword() );
        assertEquals(b, true);
    }


}
