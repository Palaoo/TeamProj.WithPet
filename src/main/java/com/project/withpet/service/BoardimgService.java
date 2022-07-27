package com.project.withpet.service;

import com.project.withpet.domain.Boardimg;
import com.project.withpet.repository.BoardimgRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class BoardimgService {
    private final BoardimgRepository boardimgRepository;


    public BoardimgService(BoardimgRepository boardimgRepository) {

        this.boardimgRepository = boardimgRepository;
    }


    public Long save(Boardimg boardimg) {
        return boardimgRepository.save(boardimg).getId();
    }

    public List<Boardimg> findBoardimgs() {
        return boardimgRepository.findAll();
    }

    public Optional<Boardimg> findOne(Long boardcode){
        return boardimgRepository.findTop1ByBoardcodeOrderById(boardcode);
    }
}
