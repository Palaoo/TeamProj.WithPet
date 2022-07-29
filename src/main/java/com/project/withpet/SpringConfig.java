package com.project.withpet;

import com.project.withpet.controller.BoardController;
import com.project.withpet.controller.S3Uploader;
import com.project.withpet.repository.*;
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

    @PersistenceContext
    private EntityManager entityManager;

    public SpringConfig(UserRepository userRepository, BoardRepository boardRepository,
                        BoardimgRepository boardimgRepository, ReplyRepository replyRepository, ShopRepository shopRepository, HotelroomRepository hotelroomRepository) {

        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardimgRepository = boardimgRepository;
        this.replyRepository = replyRepository;
        this.shopRepository = shopRepository;
        this.hotelroomRepository = hotelroomRepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }

    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository);
    }

    @Bean
    public BoardimgService boardimgService() {
        return new BoardimgService(boardimgRepository);
    }

    @Bean
    public ReplyService replyService(){
        return new ReplyService(replyRepository);
    }

    @Bean
    public ShopService shopService(){
        return new ShopService(shopRepository);
    }

    @Bean
    public HotelroomService hotelroomService(){
        return new HotelroomService(hotelroomRepository);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding(("UTF-8"));
        commonsMultipartResolver.setMaxUploadSize(50*1024*1024);
        return commonsMultipartResolver;
    }


}
