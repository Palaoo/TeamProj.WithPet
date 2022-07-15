package com.project.withpet.service;

import com.project.withpet.domain.Board;
import com.project.withpet.repository.BoardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
public class BoardServiceIntegrationTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    public void 새포스트() throws Exception{
        Board board = new Board();
        board.setTitle("안녕하세요");
        board.setWriter("김민지");
        board.setContent("날씨 좋네요");
        Long code = boardService.newPost(board);
        Optional<Board> post = boardRepository.findById(code);
        assertEquals(post.get().getTitle(), board.getTitle());
        assertEquals(post.get().getContent(), board.getContent());
        assertEquals(post.get().getWriter(), board.getWriter());
    }
}
