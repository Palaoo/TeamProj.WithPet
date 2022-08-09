package com.project.withpet;

import com.project.withpet.repository.Basket.BasketRepository;
import com.project.withpet.repository.Board.BoardRepository;
import com.project.withpet.repository.Boardimg.BoardimgRepository;
import com.project.withpet.repository.BusinessUser.BusinessUserRepository;
import com.project.withpet.repository.Cimg.CimgRepository;
import com.project.withpet.repository.Hotelroom.HotelroomRepository;
import com.project.withpet.repository.Hotelroom.Img.ImgRepository;
import com.project.withpet.repository.Like.JpaLikeRepository;
import com.project.withpet.repository.Like.LikeRepository;
import com.project.withpet.repository.LikeHotel.JpaLikeHotelRepository;
import com.project.withpet.repository.LikeHotel.LikeHotelRepository;
import com.project.withpet.repository.Order.OrderRepository;
import com.project.withpet.repository.Orderprod.OrderprodRepository;
import com.project.withpet.repository.Prod.ProdRepository;
import com.project.withpet.repository.ProdReview.JpaProdReviewRepository;
import com.project.withpet.repository.ProdReview.ProdReviewRepository;
import com.project.withpet.repository.Reply.ReplyRepository;
import com.project.withpet.repository.Shop.ShopRepository;
import com.project.withpet.repository.ShopLike.JpaShopLikeRepository;
import com.project.withpet.repository.ShopLike.ShopLikeRepository;
import com.project.withpet.repository.User.UserRepository;
import com.project.withpet.repository.shopreviewRepository;
import com.project.withpet.service.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardimgRepository boardimgRepository;
    private final ReplyRepository replyRepository;
    private final ShopRepository shopRepository;

    private final HotelroomRepository hotelroomRepository;

    private final BusinessUserRepository businessUserRepository;
    private final ProdRepository prodRepository;
    private final ImgRepository imgRepository;
    private final CimgRepository cimgRepository;
    private final OrderRepository orderRepository;
    private final OrderprodRepository orderprodRepository;
    private final shopreviewRepository shopreviewRepository;


    @PersistenceContext
    private EntityManager entityManager;
    private final BasketRepository basketRepository;

    public SpringConfig(UserRepository userRepository, BoardRepository boardRepository,
                        BoardimgRepository boardimgRepository, ReplyRepository replyRepository,
                        ShopRepository shopRepository, HotelroomRepository hotelroomRepository,
                        BusinessUserRepository businessUserRepository, ProdRepository prodRepository,
                        ImgRepository imgRepository, CimgRepository cimgRepository, OrderRepository orderRepository,
                        OrderprodRepository orderprodRepository, com.project.withpet.repository.shopreviewRepository shopreviewRepository, BasketRepository basketRepository) {

        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardimgRepository = boardimgRepository;
        this.replyRepository = replyRepository;
        this.shopRepository = shopRepository;
        this.hotelroomRepository = hotelroomRepository;
        this.businessUserRepository = businessUserRepository;
        this.prodRepository = prodRepository;
        this.imgRepository = imgRepository;
        this.cimgRepository = cimgRepository;
        this.orderRepository = orderRepository;
        this.orderprodRepository = orderprodRepository;
        this.shopreviewRepository = shopreviewRepository;
        this.basketRepository = basketRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository);
    }


    @Bean
    public BoardimgService boardimgService() {
        return new BoardimgService(boardimgRepository);
    }

    @Bean
    public ReplyService replyService() {
        return new ReplyService(replyRepository);
    }

    @Bean
    public ShopService shopService() {
        return new ShopService(shopRepository);
    }

    @Bean
    public HotelroomService hotelroomService() {
        return new HotelroomService(hotelroomRepository);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(("UTF-8"));
        commonsMultipartResolver.setMaxUploadSize(50 * 1024 * 1024);
        return commonsMultipartResolver;
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


    public LikeRepository likeRepository() {
        return new JpaLikeRepository(entityManager);
    }

    @Bean
    public BasketService basketService() {
        return new BasketService(basketRepository);
    }

    @Bean
    public ProdReviewService prodReviewService() {
        return new ProdReviewService(prodReviewRepository());
    }


    public ProdReviewRepository prodReviewRepository() {
        return new JpaProdReviewRepository(entityManager);
    }

    @Bean
    public ShopLikeService shopLikeService(){
        return new ShopLikeService(shopLikeRepository());
    }

    @Bean
    public ShopLikeRepository shopLikeRepository() {
        return new JpaShopLikeRepository(entityManager);

    }

    @Bean
    public LikeHotelService likeHotelService() {
        return new LikeHotelService(likeHotelRepository());
    }

    @Bean
    public LikeHotelRepository likeHotelRepository() {
        return new JpaLikeHotelRepository(entityManager);
    }



}
