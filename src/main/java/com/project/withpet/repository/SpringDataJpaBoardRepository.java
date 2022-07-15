package com.project.withpet.repository;

import com.project.withpet.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaBoardRepository extends JpaRepository<Board, Long>, BoardRepository {
}
