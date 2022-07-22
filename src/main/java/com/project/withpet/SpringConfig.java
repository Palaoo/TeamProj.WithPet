package com.project.withpet;

import com.project.withpet.repository.*;
import com.project.withpet.service.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
