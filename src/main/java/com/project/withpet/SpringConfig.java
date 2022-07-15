package com.project.withpet;

import com.project.withpet.domain.Board;
import com.project.withpet.repository.BoardRepository;
import com.project.withpet.repository.UserRepository;
import com.project.withpet.service.BoardService;
import com.project.withpet.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public SpringConfig(UserRepository userRepository, BoardRepository boardRepository) {

        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Bean
    public UserService userService(){
        return new UserService(userRepository);
    }

    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository);
    }
}
