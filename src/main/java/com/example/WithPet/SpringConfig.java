package com.example.WithPet;

import com.example.WithPet.Controller.ProdController;
import com.example.WithPet.Service.BusinessUserService;
import com.example.WithPet.Service.UserService;
import com.example.WithPet.repository.BusinessUser.BusinessUserRepository;
import com.example.WithPet.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final BusinessUserRepository businessUserRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, BusinessUserRepository businessUserRepository) {
        this.userRepository = userRepository;
        this.businessUserRepository = businessUserRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public BusinessUserService businessUserService() {
        return new BusinessUserService(businessUserRepository);
    }


}
