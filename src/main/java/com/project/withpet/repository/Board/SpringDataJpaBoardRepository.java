package com.project.withpet.repository.Board;

import com.project.withpet.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaBoardRepository extends JpaRepository<Board, Long>, BoardRepository {

}
