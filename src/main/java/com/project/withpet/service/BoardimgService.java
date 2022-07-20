package com.project.withpet.service;

import com.project.withpet.domain.Boardimg;
import com.project.withpet.repository.BoardimgRepository;

import javax.transaction.Transactional;
import java.nio.file.Path;
import java.util.List;

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
}
