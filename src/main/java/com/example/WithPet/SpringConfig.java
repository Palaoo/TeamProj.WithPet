package com.example.WithPet;

import com.example.WithPet.Service.*;
import com.example.WithPet.repository.Basket.BasketRepository;
import com.example.WithPet.repository.BusinessUser.BusinessUserRepository;
import com.example.WithPet.repository.Cimg.CimgRepository;
import com.example.WithPet.repository.Img.ImgRepository;
import com.example.WithPet.repository.Order.OrderRepository;
import com.example.WithPet.repository.Orderprod.OrderprodRepository;
import com.example.WithPet.repository.Prod.ProdRepository;
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
    private final CimgRepository cimgRepository;
    private final OrderRepository orderRepository;
    private final OrderprodRepository orderprodRepository;
    private final BasketRepository bascketRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, BusinessUserRepository businessUserRepository,
                        ProdRepository prodRepository, ImgRepository imgRepository, CimgRepository cimgRepository, OrderRepository orderRepository, OrderprodRepository orderprodRepository, BasketRepository bascketRepository) {
        this.userRepository = userRepository;
        this.businessUserRepository = businessUserRepository;
        this.prodRepository = prodRepository;
        this.imgRepository = imgRepository;
        this.cimgRepository = cimgRepository;
        this.orderRepository = orderRepository;
        this.orderprodRepository = orderprodRepository;
        this.bascketRepository = bascketRepository;
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

    @Bean
    public CimgService cimgService() {
        return new CimgService(cimgRepository);
    }

    @Bean
    public OrderService orderService() {
        return new OrderService(orderRepository);
    }

    @Bean
    public OrderprodService orderprodService() {
        return new OrderprodService(orderprodRepository);
    }

    @Bean
    public BasketService bascketService() {
        return new BasketService(bascketRepository);
    }
}
