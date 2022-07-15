package com.project.withpet.repository;

import com.project.withpet.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BoardRepository {

    Board save(Board board);
    Optional<Board> findById(Long boardCode);
    List<Board> findAll();

}
