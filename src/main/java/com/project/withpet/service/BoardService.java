package com.project.withpet.service;

import com.project.withpet.domain.Board;
import com.project.withpet.repository.BoardRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Long newPost(Board board){
        boardRepository.save(board);
        return board.getBoardcode();
    }

    public Optional<Board> findById(Long boardcode){
        return boardRepository.findById(boardcode);
    }
}
