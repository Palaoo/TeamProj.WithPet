package com.project.withpet.repository;

import com.project.withpet.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface SpringDataJpaBoardRepository extends JpaRepository<Board, Long>, BoardRepository {

}
