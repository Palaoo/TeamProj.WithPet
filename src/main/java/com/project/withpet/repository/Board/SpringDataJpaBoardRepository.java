package com.project.withpet.repository.Board;

import com.project.withpet.domain.Board;
import com.project.withpet.repository.Board.BoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaBoardRepository extends JpaRepository<Board, Long>, BoardRepository {

}
