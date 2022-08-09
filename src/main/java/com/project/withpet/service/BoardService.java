package com.project.withpet.service;

import com.project.withpet.domain.Board;
import com.project.withpet.repository.Board.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
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

    public Page<Board> postList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Long getLastBoardCode(){
        return boardRepository.findFirstByOrderByBoardcodeDesc().get().getBoardcode();
    }

    public void deletePost(Long boardcode){
        boardRepository.deleteById(boardcode);
    }

    public List<Board> findByUserid(String userid){
        return boardRepository.findByWriter(userid);
    }
}
