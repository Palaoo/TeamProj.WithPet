package com.project.withpet;

import com.project.withpet.repository.BoardRepository;
import com.project.withpet.repository.BoardimgRepository;
import com.project.withpet.repository.ReplyRepository;
import com.project.withpet.repository.UserRepository;
import com.project.withpet.service.BoardService;
import com.project.withpet.service.BoardimgService;
import com.project.withpet.service.ReplyService;
import com.project.withpet.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardimgRepository boardimgRepository;

    private final ReplyRepository replyRepository;

    public SpringConfig(UserRepository userRepository, BoardRepository boardRepository, BoardimgRepository boardimgRepository, ReplyRepository replyRepository) {

        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardimgRepository = boardimgRepository;
        this.replyRepository = replyRepository;
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
}
