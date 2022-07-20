package com.project.withpet.repository;

import com.project.withpet.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface BoardRepository {

    Board save(Board board);
    Optional<Board> findById(Long boardCode);

    Page<Board> findAll(Pageable pageable);

    Optional<Board> findFirstByOrderByBoardcodeDesc();
}
