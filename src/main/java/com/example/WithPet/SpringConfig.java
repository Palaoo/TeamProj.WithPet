package com.example.WithPet;

import com.example.WithPet.Service.*;
import com.example.WithPet.repository.Basket.BasketRepository;
import com.example.WithPet.repository.BusinessUser.BusinessUserRepository;
import com.example.WithPet.repository.Cimg.CimgRepository;
import com.example.WithPet.repository.Img.ImgRepository;
import com.example.WithPet.repository.Like.JpaLikeRepository;
import com.example.WithPet.repository.Like.LikeRepository;
import com.example.WithPet.repository.Order.OrderRepository;
import com.example.WithPet.repository.Orderprod.OrderprodRepository;
import com.example.WithPet.repository.Prod.ProdRepository;
import com.example.WithPet.repository.ProdReview.JpaProdReviewRepository;
import com.example.WithPet.repository.ProdReview.ProdReviewRepository;
import com.example.WithPet.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManager;

@Configuration
public class SpringConfig {
    private final UserRepository userRepository;
    private final BusinessUserRepository businessUserRepository;
    private final ProdRepository prodRepository;
    private final ImgRepository imgRepository;
    private final CimgRepository cimgRepository;
    private final OrderRepository orderRepository;
    private final OrderprodRepository orderprodRepository;
    private final EntityManager em;
    private final BasketRepository basketRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, BusinessUserRepository businessUserRepository,
                        ProdRepository prodRepository, ImgRepository imgRepository, CimgRepository cimgRepository,
                        OrderRepository orderRepository, OrderprodRepository orderprodRepository,
                        EntityManager em, BasketRepository basketRepository) {
        this.userRepository = userRepository;
        this.businessUserRepository = businessUserRepository;
        this.prodRepository = prodRepository;
        this.imgRepository = imgRepository;
        this.cimgRepository = cimgRepository;
        this.orderRepository = orderRepository;
        this.orderprodRepository = orderprodRepository;
        this.em = em;
        this.basketRepository = basketRepository;
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
    public LikeService likeService() {
        return new LikeService(likeRepository());
    }

    @Bean
    public LikeRepository likeRepository() {
        return new JpaLikeRepository(em);
    }

    @Bean
    public BasketService basketService() {
        return new BasketService(basketRepository);
    }

    @Bean
    public ProdReviewService prodReviewService() {
        return new ProdReviewService(prodReviewRepository());
    }

    @Bean
    public ProdReviewRepository prodReviewRepository() {
        return new JpaProdReviewRepository(em);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(("UTF-8"));
        commonsMultipartResolver.setMaxUploadSize(50*1024*1024);
        return commonsMultipartResolver;
    }
}
