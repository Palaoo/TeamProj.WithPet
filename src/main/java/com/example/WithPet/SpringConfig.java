package com.example.WithPet;

import com.example.WithPet.Service.BusinessUserService;
import com.example.WithPet.Service.ImgService;
import com.example.WithPet.Service.ProdService;
import com.example.WithPet.Service.UserService;
import com.example.WithPet.repository.BusinessUser.BusinessUserRepository;
import com.example.WithPet.repository.Img.ImgRepository;
import com.example.WithPet.repository.Prod.ProdRepository;
import com.example.WithPet.repository.Prod.SpringDataJpaProd;
import com.example.WithPet.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final BusinessUserRepository businessUserRepository;
    private final ProdRepository prodRepository;
    private final ImgRepository imgRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, BusinessUserRepository businessUserRepository,
                        ProdRepository prodRepository, ImgRepository imgRepository) {
        this.userRepository = userRepository;
        this.businessUserRepository = businessUserRepository;
        this.prodRepository = prodRepository;
        this.imgRepository = imgRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public BusinessUserService businessUserService() {
        return new BusinessUserService(businessUserRepository);
    }

    @Bean
    public ProdService prodService() {
        return new ProdService(prodRepository);
    }

    @Bean
    public ImgService imgService() {
        return new ImgService(imgRepository);
    }

}
