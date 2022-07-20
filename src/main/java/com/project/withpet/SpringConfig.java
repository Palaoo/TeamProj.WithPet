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

    private final JPAQueryFactory jpaQueryFactory;

    private final ShopRepositoryCustom shopRepositoryCustom;

    public SpringConfig(UserRepository userRepository, BoardRepository boardRepository, BoardimgRepository boardimgRepository, ReplyRepository replyRepository, EntityManager entityManager, JPAQueryFactory jpaQueryFactory, ShopRepositoryCustom shopRepositoryCustom) {

        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardimgRepository = boardimgRepository;
        this.replyRepository = replyRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.shopRepositoryCustom = shopRepositoryCustom;
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
    public ShopRepositoryImpl shopService(){
        return new ShopRepositoryImpl(jpaQueryFactory, shopRepositoryCustom);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(entityManager);
    }
}
