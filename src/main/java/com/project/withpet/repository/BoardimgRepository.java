package com.project.withpet.repository;

import com.project.withpet.domain.Boardimg;

import java.util.List;
import java.util.Optional;

public interface BoardimgRepository {
    Boardimg save(Boardimg boardimg);

    Optional<Boardimg> findById(Long id);

    Optional<Boardimg> findByName(String name);

    List<Boardimg> findAll();

    Optional<Boardimg> findTop1ByBoardcodeOrderById(Long boardcode);

    void deleteByBoardcode(Long boardcode);
}
